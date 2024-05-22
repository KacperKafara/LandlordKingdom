package pl.lodz.p.it.ssbd2024.mok.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.messages.VerificationTokenMessages;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.*;
import pl.lodz.p.it.ssbd2024.mok.dto.oauth.GoogleOAuth2TokenResponse;
import pl.lodz.p.it.ssbd2024.mok.dto.oauth.GoogleOAuth2TokenPayload;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;
import pl.lodz.p.it.ssbd2024.mok.services.impl.JwtService;

import java.security.InvalidKeyException;
import java.util.Base64;
import java.util.List;
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
    private final JwtService jwtService;

    @Value("${oauth2.auth.url}")
    private String oAuth2Url;

    @Value("${oauth2.client.id}")
    private String oAuthClientId;

    @Value("${oauth2.client.secret}")
    private String oAuthClientSecret;

    @Value("${oauth2.redirect.url}")
    private String oAuthRedirectUri;

    @Value("${oauth2.token.url}")
    private String oAuthTokenUri;

    @PostMapping("/signup")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserCreateRequest newUserData) {
        try {
            User newUser = new User(
                    newUserData.firstName(),
                    newUserData.lastName(),
                    newUserData.email(),
                    newUserData.login(),
                    newUserData.language()
            );
            userService.createUser(newUser, newUserData.password());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED);
        } catch (IdenticalFieldValueException | CreationException e) {
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


    @PostMapping("/verify-2fa")
    public ResponseEntity<AuthenticationResponse> verify2faCode(@RequestBody @Valid Verify2FATokenRequest request) {
        try {
            Map<String, String> tokens = authenticationService.verifyOTP(request.token(), request.login(), servletRequest.getRemoteAddr());
            return ResponseEntity.ok(new AuthenticationResponse(tokens.get("token"), tokens.get("refreshToken")));
        } catch (VerificationTokenUsedException | VerificationTokenExpiredException | LoginNotMatchToOTPException e) {
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
    public ResponseEntity<AuthenticationResponse> getOAuth2Token(@RequestParam String code) throws JsonProcessingException {
        String url = UriComponentsBuilder
                .fromUriString(oAuthTokenUri)
                .queryParam("client_id", oAuthClientId)
                .queryParam("client_secret", oAuthClientSecret)
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .queryParam("redirect_uri", oAuthRedirectUri)
                .build().toUriString();
        RestClient restClient = RestClient.create();
        GoogleOAuth2TokenResponse result = restClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(GoogleOAuth2TokenResponse.class);

        String token = result.getIdToken();
        String[] tokenChunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String tokenPayload = new String(decoder.decode(tokenChunks[1]));
        ObjectMapper mapper = new ObjectMapper();
        GoogleOAuth2TokenPayload payload = mapper.readValue(tokenPayload, GoogleOAuth2TokenPayload.class);

        Map<String, String> response;
        try {
            User user;
            try {
                user = userService.getUserByGoogleId(payload.getSub());

                if (!user.isVerified()) {
                    throw new UserNotVerifiedException(UserExceptionMessages.NOT_VERIFIED);
                }

                List<String> roles = userService.getUserRoles(user.getId());
                String userToken = jwtService.generateToken(user.getId(), roles);
                String refreshToken = jwtService.generateRefreshToken(user.getId());

                response = Map.of(
                        "token", userToken,
                        "refreshToken", refreshToken);

            } catch (NotFoundException e) {
                User newUser = new User(
                        payload.getGivenName(),
                        payload.getFamilyName(),
                        payload.getEmail(),
                        payload.getEmail(),
                        "en"
                );
                newUser.setGoogleId(payload.getSub());
                userService.createUser(newUser);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }

        } catch (TokenGenerationException e1) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED);
        } catch (IdenticalFieldValueException | CreationException e1) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e1.getMessage());
        } catch (UserNotVerifiedException e1) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e1.getMessage());
        }

        return ResponseEntity.ok(new AuthenticationResponse(response.get("token"), response.get("refreshToken")));
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