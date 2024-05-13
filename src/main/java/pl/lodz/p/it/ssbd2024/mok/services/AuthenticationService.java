package pl.lodz.p.it.ssbd2024.mok.services;

import org.springframework.security.oauth2.jwt.Jwt;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.model.User;

import java.security.InvalidKeyException;
import java.util.List;
import java.util.Map;

public interface AuthenticationService {
    List<String> getUserRoles(User user);

    String authenticate(String login, String password, String ip) throws NotFoundException, UserNotVerifiedException, UserBlockedException, InvalidLoginDataException, SignInBlockedException;

    void verify(String token) throws VerificationTokenUsedException, VerificationTokenExpiredException, NotFoundException;

    void generateOTP(String login, String password, String language, String ip) throws InvalidKeyException, NotFoundException, UserNotVerifiedException, UserBlockedException, SignInBlockedException, InvalidLoginDataException;

    Map<String, String> verifyOTP(String token) throws VerificationTokenUsedException, VerificationTokenExpiredException;

    Map<String, String> refresh(String refreshToken) throws NotFoundException, RefreshTokenExpiredException;
}
