package pl.lodz.p.it.ssbd2024.mok.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.TokenGenerationException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.messages.VerificationTokenMessages;
import pl.lodz.p.it.ssbd2024.mok.dto.UserEmailUpdateRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.DetailedUserResponse;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.UpdateUserDataRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UserResponse;
import pl.lodz.p.it.ssbd2024.mok.mappers.UserMapper;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream().map(UserMapper::toUserResponse).toList());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/user/{id}")
    public ResponseEntity<DetailedUserResponse> get(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok(UserMapper.toDetailedUserResponse(userService.getUserById(id)));
    }

    
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/user/login/{login}")
    public ResponseEntity<DetailedUserResponse> get(@PathVariable String login)  {
        try {
            return ResponseEntity.ok(UserMapper.toDetailedUserResponse(userService.getUserByLogin(login)));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable UUID id) {
        try {
            userService.blockUser(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/unblock")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> unblockUser(@PathVariable UUID id) {
        try {
            userService.unblockUser(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/update-data")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<UserResponse> updateUserData(@PathVariable UUID id,
                                                       @RequestBody UpdateUserDataRequest request) {
        try {
            User user = userService.updateUserData(id, UserMapper.toUser(request));
            return ResponseEntity.ok(UserMapper.toUserResponse(user));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{id}/email-update-request")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> sendUpdateEmail(@PathVariable UUID id) {
        try {
            userService.sendUpdateEmail(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/update-email")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> updateUserEmail(@RequestBody UserEmailUpdateRequest request) throws VerificationTokenUsedException, NotFoundException, VerificationTokenExpiredException {
        userService.changeUserEmail(request.token(), request.email());
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    @PostMapping("/reset-password")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> resetPassword(@RequestParam String email) {
        try {
            userService.resetUserPassword(email);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED);
        }
        return ResponseEntity.noContent().build();
    }
}
