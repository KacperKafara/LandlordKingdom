package pl.lodz.p.it.ssbd2024.mok.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationResponse;
import pl.lodz.p.it.ssbd2024.mok.dto.UserCreateRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.VerifyUserRequest;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;

@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final HttpServletRequest servletRequest;

    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserCreateRequest newUserData) {
        User newUser = new User(
                newUserData.firstName(),
                newUserData.lastName(),
                newUserData.email(),
                newUserData.login()
        );
        try {
            userService.createUser(newUser, newUserData.password());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IdenticalFieldValueException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            String token = authenticationService.authenticate(request.getLogin(), request.getPassword(), servletRequest.getRemoteAddr());
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UserNotVerifiedException | SignInBlockedException | UserBlockedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (InvalidLoginDataException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyUserRequest request) throws NotFoundException {
        try {
            authenticationService.verify(request.token());
            return ResponseEntity.ok().build();
        } catch (VerificationTokenUsedException | VerificationTokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}