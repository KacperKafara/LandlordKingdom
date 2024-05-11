package pl.lodz.p.it.ssbd2024.mok.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.messages.AdministratorMessages;
import pl.lodz.p.it.ssbd2024.messages.OptimisticLockExceptionMessages;
import pl.lodz.p.it.ssbd2024.messages.VerificationTokenMessages;
import pl.lodz.p.it.ssbd2024.mok.dto.UserEmailUpdateRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.DetailedUserResponse;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.UpdateUserDataRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UserResponse;
import pl.lodz.p.it.ssbd2024.mok.mappers.UserMapper;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;
import pl.lodz.p.it.ssbd2024.util.Signer;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final Signer signer;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream().map(UserMapper::toUserResponse).toList());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<DetailedUserResponse> get(@PathVariable UUID id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.ETAG, signer.generateSignature(user.getId(), user.getVersion()))
                    .body(UserMapper.toDetailedUserResponse(user));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID administratorId = UUID.fromString(jwt.getSubject());

        if(administratorId.equals(id)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, AdministratorMessages.OWN_ADMINISTRATOR_BLOCK);
        }

        try {
            userService.blockUser(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/{id}/unblock")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> unblockUser(@PathVariable UUID id) {
        try {
            userService.unblockUser(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}/update-data")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<UserResponse> updateUserData(@PathVariable UUID id,
                                                       @RequestBody @Valid UpdateUserDataRequest request,
                                                       @RequestHeader(HttpHeaders.IF_MATCH) String tagValue
    ) {
        try {
            User checkUser = userService.getUserById(id);
            if (!signer.verifySignature(checkUser.getId(), checkUser.getVersion(), tagValue)) {
                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, OptimisticLockExceptionMessages.USER_ALREADY_MODIFIED_DATA);
            }

            User user = userService.updateUserData(id, UserMapper.toUser(request));
            return ResponseEntity.ok(UserMapper.toUserResponse(user));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{id}/email-update-request")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> sendUpdateEmail(@PathVariable UUID id) {
        try {
            userService.sendEmailUpdateEmail(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/update-email")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> updateUserEmail(@RequestBody @Valid UserEmailUpdateRequest request) {
        try {
            userService.changeUserEmail(request.token(), request.email());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (VerificationTokenUsedException | NotFoundException | VerificationTokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> resetPassword(@RequestParam String email) {
        try {
            userService.sendChangePasswordEmail(email);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED);
        } catch (UserBlockedException | UserNotVerifiedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
