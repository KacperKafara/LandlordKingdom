package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.model.VerificationToken;

public interface VerificationTokenService {

    String generateAccountVerificationToken(User user);

    VerificationToken validateAccountVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;

    String generateEmailVerificationToken(User user);

    VerificationToken validateEmailVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;

    String generatePasswordVerificationToken(User user);

    VerificationToken validatePasswordVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;



}
