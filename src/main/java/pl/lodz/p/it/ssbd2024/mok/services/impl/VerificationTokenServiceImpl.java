package pl.lodz.p.it.ssbd2024.mok.services.impl;

import com.eatthepath.otp.HmacOneTimePasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.TokenGenerationException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.VerificationTokenMessages;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.model.tokens.*;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AccountActivateTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.AccountVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.EmailVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.OTPTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.PasswordVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
@Transactional(propagation = Propagation.MANDATORY, rollbackFor = TokenGenerationException.class)
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final AccountVerificationTokenRepository accountTokenRepository;
    private final EmailVerificationTokenRepository emailTokenRepository;
    private final PasswordVerificationTokenRepository passwordTokenRepository;
    private final AccountActivateTokenRepository accountActivateTokenRepository;
    private final OTPTokenRepository otpTokenRepository;
    private final HmacOneTimePasswordGenerator hotp = new HmacOneTimePasswordGenerator(8);
    private long counter = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) / 30;

    @Value("${otp.secret}")
    private String otpSecret;

    @Override
    @PreAuthorize("permitAll()")
    public User getUserByEmailToken(String token) throws VerificationTokenUsedException {
        return emailTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED)).getUser();
    }

    public User getUserByPasswordToken(String token) throws VerificationTokenUsedException {
        return passwordTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED)).getUser();
    }

    @Override
    @PreAuthorize("permitAll()")
    public String generateAccountVerificationToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        accountTokenRepository.deleteByUserId(user.getId());
        accountTokenRepository.flush();
        AccountVerificationToken token = new AccountVerificationToken(tokenVal, Instant.now().plus(AccountVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return accountTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validateAccountVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        VerificationToken verificationToken = accountTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED, ErrorCodes.VERIFICATION_TOKEN_EXPIRED);
        }
        accountTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public String generateEmailVerificationToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        emailTokenRepository.deleteEmailVerificationTokenByUserId(user.getId());
        accountTokenRepository.flush();
        EmailVerificationToken token = new EmailVerificationToken(tokenVal, Instant.now().plus(EmailVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return emailTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validateEmailVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        EmailVerificationToken verificationToken = emailTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED, ErrorCodes.VERIFICATION_TOKEN_EXPIRED);
        }
        emailTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    @PreAuthorize("permitAll()")
    public String generatePasswordVerificationToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        passwordTokenRepository.deleteByUserId(user.getId());
        accountTokenRepository.flush();
        PasswordVerificationToken token = new PasswordVerificationToken(tokenVal, Instant.now().plus(PasswordVerificationToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return passwordTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validatePasswordVerificationToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        PasswordVerificationToken verificationToken = passwordTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED, ErrorCodes.VERIFICATION_TOKEN_EXPIRED);
        }
        passwordTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    @PreAuthorize("permitAll()")
    public String generateAccountActivateToken(User user) throws TokenGenerationException {
        String tokenVal = generateSafeToken();
        accountActivateTokenRepository.deleteByUserId(user.getId());
        accountActivateTokenRepository.flush();
        AccountActivateToken token = new AccountActivateToken(tokenVal, Instant.now().plus(AccountActivateToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return accountActivateTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {VerificationTokenExpiredException.class, VerificationTokenUsedException.class})
    public VerificationToken validateAccountActivateToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        AccountActivateToken verificationToken = accountActivateTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED, ErrorCodes.VERIFICATION_TOKEN_EXPIRED);
        }
        passwordTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @Override
    @PreAuthorize("permitAll()")
    public String generateOTPToken(User user) throws InvalidKeyException {
        byte[] bytes = otpSecret.getBytes();
        SecretKey key = new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA1");
        String tokenVal = hotp.generateOneTimePasswordString(key, counter++);
        otpTokenRepository.deleteByUserId(user.getId());
        otpTokenRepository.flush();
        OTPToken token = new OTPToken(tokenVal, Instant.now().plus(OTPToken.EXPIRATION_TIME, ChronoUnit.MINUTES), user);
        return otpTokenRepository.saveAndFlush(token).getToken();
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(propagation = Propagation.MANDATORY)
    public VerificationToken validateOTPToken(String token) throws VerificationTokenExpiredException, VerificationTokenUsedException {
        OTPToken verificationToken = otpTokenRepository.findByToken(token).orElseThrow(() -> new VerificationTokenUsedException(VerificationTokenMessages.VERIFICATION_TOKEN_USED, ErrorCodes.VERIFICATION_TOKEN_USED));
        if (verificationToken.getExpirationDate().isBefore(Instant.now())) {
            throw new VerificationTokenExpiredException(VerificationTokenMessages.VERIFICATION_TOKEN_EXPIRED, ErrorCodes.VERIFICATION_TOKEN_EXPIRED);
        }
        otpTokenRepository.deleteById(verificationToken.getId());
        return verificationToken;
    }

    @PreAuthorize("permitAll()")
    private String generateSafeToken() throws TokenGenerationException {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        try {
            SecureRandom random = SecureRandom.getInstanceStrong();
            return random.ints(32, 0, chars.length())
                    .mapToObj(chars::charAt)
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString();
        } catch (NoSuchAlgorithmException e) {
            throw new TokenGenerationException(e.getMessage(), ErrorCodes.INTERNAL_SERVER_ERROR);
        }

    }
}
