package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.model.User;

import java.security.InvalidKeyException;
import java.util.List;

public interface AuthenticationService {
    List<String> getUserRoles(User user);

    void verify(String token) throws VerificationTokenUsedException, VerificationTokenExpiredException, NotFoundException;

    void generateOTP(String login, String password, String language, String ip) throws InvalidKeyException, NotFoundException, UserNotVerifiedException, UserBlockedException, SignInBlockedException, InvalidLoginDataException;

    String verifyOTP(String token, String login, String ip) throws VerificationTokenUsedException, VerificationTokenExpiredException, NotFoundException, LoginNotMatchToOTPException;
}
