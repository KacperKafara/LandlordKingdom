package pl.lodz.p.it.ssbd2024.mok.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.InvalidPasswordException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.TokenGenerationException;
import pl.lodz.p.it.ssbd2024.messages.OptimisticLockExceptionMessages;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticatedChangePasswordRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.ChangePasswordRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UpdateUserDataRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UserEmailUpdateRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UserResponse;
import pl.lodz.p.it.ssbd2024.mok.mappers.UserMapper;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;
import pl.lodz.p.it.ssbd2024.util.Signer;

import java.util.UUID;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeController {

    private final UserService userService;
    private final Signer signer;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getUserData() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            UUID id = UUID.fromString(jwt.getSubject());
            User user = userService.getUserById(id);

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.ETAG, signer.generateSignature(user.getId(), user.getVersion()))
                    .body(UserMapper.toUserResponse(user));

        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> updateUserData(
            @Validated @RequestBody UpdateUserDataRequest request,
            @RequestHeader(HttpHeaders.IF_MATCH) String tagValue){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            UUID id = UUID.fromString(jwt.getSubject());

            User checkUser = userService.getUserById(id);
            if (!signer.verifySignature(checkUser.getId(), checkUser.getVersion(), tagValue)) {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, OptimisticLockExceptionMessages.USER_ALREADY_MODIFIED_DATA);
            }

            User user = userService.updateUserData(id, UserMapper.toUser(request));
            return ResponseEntity.ok(UserMapper.toUserResponse(user));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity changePassword(@RequestBody AuthenticatedChangePasswordRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwt = (Jwt) authentication.getPrincipal();
            UUID id = UUID.fromString(jwt.getSubject());

            userService.changePassword(id, request.oldPassword(), request.newPassword());

            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/change-password-with-token")
    public ResponseEntity changePasswordWithToken(@RequestBody ChangePasswordRequest request) {
        try {
            userService.changePasswordWithToken(request.password(), request.token());
            return ResponseEntity.ok().build();
        } catch (VerificationTokenUsedException | VerificationTokenExpiredException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/email-update-request")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OWNER', 'TENANT')")
    public ResponseEntity<String> sendUpdateEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID id = UUID.fromString(jwt.getSubject());
        try {
            userService.sendUpdateEmail(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (TokenGenerationException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/update-email")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> updateUserEmail(@RequestBody UserEmailUpdateRequest request) {
        try {
            userService.changeUserEmail(request.token(), request.email());
        } catch (NotFoundException | VerificationTokenUsedException | VerificationTokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
