package pl.lodz.p.it.ssbd2024.mok.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.mok.dto.UserEmailUpdateRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UserResponse;
import pl.lodz.p.it.ssbd2024.mok.mappers.UserMapper;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream().map(UserMapper::toUserResponse).toList());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/block")
    public ResponseEntity<String> blockUser(@RequestBody UUID id) {
        try {
            userService.blockUser(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/unblock")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> unblockUser(@RequestBody UUID id) {
        try {
            userService.unblockUser(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/email-update-request")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<String> sendUpdateEmail(@RequestBody UUID id) throws NotFoundException {
        userService.sendUpdateEmail(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/update-email")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> updateUserEmail(@RequestBody UserEmailUpdateRequest request) throws VerificationTokenUsedException, NotFoundException, VerificationTokenExpiredException {
        userService.changeUserEmail(request.token(), request.email());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
