package pl.lodz.p.it.ssbd2024.mok.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationResponse;
import pl.lodz.p.it.ssbd2024.mok.dto.UserCreateRequest;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;

import java.net.URI;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> registerUser(@RequestBody UserCreateRequest newUserData) {
        User newUser = new User(
                newUserData.firstName(),
                newUserData.lastName(),
                newUserData.email(),
                newUserData.login()
        );
        userService.registerUser(newUser, newUserData.password());

        URI userLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/auth/")
                .buildAndExpand(newUser.getId())
                .toUri();
        return ResponseEntity.created(userLocation).build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request.getLogin(), request.getPassword()));
    }
}