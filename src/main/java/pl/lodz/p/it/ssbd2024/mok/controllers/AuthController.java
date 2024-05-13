package pl.lodz.p.it.ssbd2024.mok.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.messages.VerificationTokenMessages;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.*;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;

import java.security.InvalidKeyException;
import java.util.Map;

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
        try {
            User newUser = new User(
                    newUserData.firstName(),
                    newUserData.lastName(),
                    newUserData.email(),
                    newUserData.login()
            );
            userService.createUser(newUser, newUserData.password());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED);
        } catch (IdenticalFieldValueException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }
    }

    @PostMapping("/signin-2fa")
    public ResponseEntity<Void> authenticate2fa(@RequestBody @Valid AuthenticationRequest request) {
        try {
            authenticationService.generateOTP(request.login(), request.password(), request.language(), servletRequest.getRemoteAddr());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UserNotVerifiedException | SignInBlockedException | UserBlockedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (InvalidLoginDataException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (InvalidKeyException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "There was an error generating OTP");
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verify(@RequestBody @Valid VerifyUserRequest request) throws NotFoundException {
        try {
            authenticationService.verify(request.token());
            return ResponseEntity.ok().build();
        } catch (VerificationTokenUsedException | VerificationTokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<AuthenticationResponse> verify2faCode(@RequestBody @Valid Verify2FATokenRequest request) {
        try {
            Map<String, String> tokens = authenticationService.verifyOTP(request.token(), request.login(), servletRequest.getRemoteAddr());
            return ResponseEntity.ok(new AuthenticationResponse(tokens.get("token"), tokens.get("refreshToken")));
        }catch (VerificationTokenUsedException | VerificationTokenExpiredException | LoginNotMatchToOTPException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/refresh")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        try {
            Map<String, String> tokens = authenticationService.refresh(refreshToken.refreshToken());
            return ResponseEntity.ok(new AuthenticationResponse(tokens.get("token"), tokens.get("refreshToken")));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (RefreshTokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}