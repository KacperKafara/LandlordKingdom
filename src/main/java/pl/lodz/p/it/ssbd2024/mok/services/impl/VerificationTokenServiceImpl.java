package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.TokenGenerationException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.messages.VerificationTokenMessages;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.mok.repositories.AccountVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.EmailVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.PasswordVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional(propagation = Propagation.MANDATORY, rollbackFor = TokenGenerationException.class)
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final AccountVerificationTokenRepository accountTokenRepository;
    private final EmailVerificationTokenRepository emailTokenRepository;
    private final PasswordVerificationTokenRepository passwordTokenRepository;

    @Override
    public String generateAccountVerificationToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        accountTokenRepository.deleteByUserId(user.getId());
        accountTokenRepository.flush();
        AccountVerificationToken token = new AccountVerificationToken(tokenVal, Instant.now().plus(AccountVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return accountTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validateAccountVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        VerificationToken verificationToken = accountTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED);
        }
        accountTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    public String generateEmailVerificationToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        emailTokenRepository.deleteEmailVerificationTokenByUserId(user.getId());
        accountTokenRepository.flush();
        EmailVerificationToken token = new EmailVerificationToken(tokenVal, Instant.now().plus(EmailVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return emailTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validateEmailVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        EmailVerificationToken verificationToken = emailTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED);
        }
        emailTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    public String generatePasswordVerificationToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        passwordTokenRepository.deleteByUserId(user.getId());
        accountTokenRepository.flush();
        PasswordVerificationToken token = new PasswordVerificationToken(tokenVal, Instant.now().plus(PasswordVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return passwordTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validatePasswordVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        PasswordVerificationToken verificationToken = passwordTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED);
        }
        passwordTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    private String generateSafeToken() throws TokenGenerationException {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            return random.ints(32, 0, chars.length())
                    .mapToObj(chars::charAt)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString();
        } catch (NoSuchAlgorithmException e) {
            throw new TokenGenerationException(e.getMessage());
        }

    }
}
