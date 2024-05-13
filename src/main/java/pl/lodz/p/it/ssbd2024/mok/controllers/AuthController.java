package pl.lodz.p.it.ssbd2024.mok.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.messages.VerificationTokenMessages;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.*;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;

import java.security.InvalidKeyException;
import java.util.Map;

@Log
@RestController()
@RequestMapping("/auth")
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final HttpServletRequest servletRequest;

    @Value("${oauth2.authUrl}")
    private String oAuth2Url;

    @Value("${oauth2.client_id}")
    private String oAuthClientId;

    @Value("${oauth2.client_secret}")
    private String oAuthClientSecret;

    @Value("${oauth2.redirect_uri}")
    private String oAuthRedirectUri;

    @Value("${oauth2.token_uri}")
    private String oAuthTokenUri;

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

    @GetMapping("/oauth2/url")
    public ResponseEntity<OAuth2UrlResponse> getOAuth2Url() {
        String url = UriComponentsBuilder
                .fromUriString(oAuth2Url)
                .queryParam("client_id", oAuthClientId)
                .queryParam("redirect_uri", oAuthRedirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "openid profile email")
                .queryParam("access_type", "offline")
                .queryParam("state", "standard_oauth")
                .queryParam("prompt", "consent")
                .build().toUriString();
        return ResponseEntity.ok(new OAuth2UrlResponse(url));
    }

    @GetMapping("/oauth2/token")
    public ResponseEntity getOAuth2Token(@RequestParam String code) {
        String url = UriComponentsBuilder
                .fromUriString(oAuthTokenUri)
                .queryParam("client_id", oAuthClientId)
                .queryParam("client_secret", oAuthClientSecret)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .queryParam("redirect_uri", oAuthRedirectUri)
                .build().toUriString();
        RestClient restClient = RestClient.create();
        String result = restClient.post().uri(url).retrieve().body(String.class);
        log.info(result);
        return ResponseEntity.ok().build();
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