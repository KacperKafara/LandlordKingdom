package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.TokenGenerationException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.model.tokens.VerificationToken;

import java.security.InvalidKeyException;

public interface VerificationTokenService {

    User getUserByEmailToken(String token) throws VerificationTokenUsedException;

    User getUserByPasswordToken(String token) throws VerificationTokenUsedException;

    String generateAccountVerificationToken(User user) throws TokenGenerationException;

    VerificationToken validateAccountVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;

    String generateEmailVerificationToken(User user) throws TokenGenerationException;

    VerificationToken validateEmailVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;

    String generatePasswordVerificationToken(User user) throws TokenGenerationException;

    VerificationToken validatePasswordVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;

    String generateAccountActivateToken(User user) throws TokenGenerationException;

    VerificationToken validateAccountActivateToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;

    String generateOTPToken(User user) throws InvalidKeyException;

    VerificationToken validateOTPToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;
}
