package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.model.VerificationToken;

public interface VerificationTokenService {

    public String generateAccountVerificationToken(User user);

    public VerificationToken validateAccountVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;

    public String generateEmailVerificationToken(User user);

    public VerificationToken validateEmailVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;

    public String generatePasswordVerificationToken(User user);

    public VerificationToken validatePasswordVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException;



}
