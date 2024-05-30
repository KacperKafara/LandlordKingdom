package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.mok.dto.oauth.GoogleOAuth2TokenPayload;

import java.security.InvalidKeyException;
import java.util.Map;

public interface AuthenticationService {
    void generateOTP(String login, String password, String language, String ip) throws InvalidKeyException, NotFoundException, UserNotVerifiedException, UserBlockedException, SignInBlockedException, InvalidLoginDataException, TokenGenerationException, UserInactiveException;

    Map<String, String> refresh(String refreshToken) throws NotFoundException, RefreshTokenExpiredException;

    Map<String, String> verifyOTP(String token, String login, String ip) throws VerificationTokenUsedException, VerificationTokenExpiredException, NotFoundException, LoginNotMatchToOTPException;

    Map<String, String> singInOAuth(String token, String ip, GoogleOAuth2TokenPayload payload) throws UserNotVerifiedException, TokenGenerationException, CreationException, IdenticalFieldValueException;
}
