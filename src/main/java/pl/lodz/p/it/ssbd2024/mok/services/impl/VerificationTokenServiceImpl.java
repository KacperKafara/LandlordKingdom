package pl.lodz.p.it.ssbd2024.mok.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.messages.VerificationTokenMessages;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.mok.repositories.AccountVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.EmailVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.PasswordVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.MANDATORY)
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final AccountVerificationTokenRepository accountTokenRepository;
    private final EmailVerificationTokenRepository emailTokenRepository;
    private final PasswordVerificationTokenRepository passwordTokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(AccountVerificationTokenRepository tokenRepository, EmailVerificationTokenRepository emailTokenRepository, PasswordVerificationTokenRepository passwordTokenRepository) {
        this.accountTokenRepository = tokenRepository;
        this.emailTokenRepository = emailTokenRepository;
        this.passwordTokenRepository = passwordTokenRepository;
    }

    @Override
    public String generateAccountVerificationToken(User user) {
        String tokenVal = UUID.randomUUID().toString();
        accountTokenRepository.deleteByUserId(user.getId());
        accountTokenRepository.flush();
        AccountVerificationToken token = new AccountVerificationToken(tokenVal, Instant.now().plus(AccountVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return accountTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    public VerificationToken validateAccountVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        VerificationToken verificationToken = accountTokenRepository.findByToken(token).orElseThrow(()  ->  new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())){
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED);
        }
        accountTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    public String generateEmailVerificationToken(User user) {
        String tokenVal = UUID.randomUUID().toString();
        emailTokenRepository.deleteEmailVerificationTokenByUserId(user.getId());
        accountTokenRepository.flush();
        EmailVerificationToken token = new EmailVerificationToken(tokenVal, Instant.now().plus(EmailVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return emailTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    public VerificationToken validateEmailVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        EmailVerificationToken verificationToken = emailTokenRepository.findByToken(token).orElseThrow(()  ->  new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())){
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED);
        }
        emailTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    public String generatePasswordVerificationToken(User user) {
        String tokenVal = UUID.randomUUID().toString();
        passwordTokenRepository.deleteByUserId(user.getId());
        accountTokenRepository.flush();
        PasswordVerificationToken token = new PasswordVerificationToken(tokenVal, Instant.now().plus(PasswordVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return passwordTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    public VerificationToken validatePasswordVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        PasswordVerificationToken verificationToken = passwordTokenRepository.findByToken(token).orElseThrow(()  ->  new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())){
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED);
        }
        passwordTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }


}
