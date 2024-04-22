package pl.lodz.p.it.ssbd2024.mok.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.UpdateUserDataRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UserResponse;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getUserData() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID id = UUID.fromString(jwt.getSubject());
        User user = userService.getUserById(id);
        return ResponseEntity.ok(
                new UserResponse(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getLogin(),
                        user.getLanguage()
                )
        );
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUserData(@Validated @RequestBody UpdateUserDataRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID id = UUID.fromString(jwt.getSubject());

        User user = userService.updateUserData(id, request.toUser());
        return ResponseEntity.ok(
                new UserResponse(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getLogin(),
                        user.getLanguage()
                )
        );
    }
}
