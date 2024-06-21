package pl.lodz.p.it.ssbd2024.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.lodz.p.it.ssbd2024.config.ToolConfig;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.model.tokens.AccountVerificationToken;
import pl.lodz.p.it.ssbd2024.model.tokens.EmailVerificationToken;
import pl.lodz.p.it.ssbd2024.model.tokens.PasswordVerificationToken;
import pl.lodz.p.it.ssbd2024.mok.repositories.AccountVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.EmailVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.PasswordVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("unit")
@SpringJUnitConfig({ToolConfig.class, MockConfig.class})
public class VerificationTokenServiceTest {

    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private AccountVerificationTokenRepository accountTokenRepository;
    @Autowired
    private EmailVerificationTokenRepository emailTokenRepository;
    @Autowired
    private PasswordVerificationTokenRepository passwordTokenRepository;

    @Test
    public void validateAccountVerificationToken_tokenExpired_throwVerificationTokenUsedException() {
        AccountVerificationToken accountVerificationToken = new AccountVerificationToken();
        accountVerificationToken.setExpirationDate(Instant.now().minusSeconds(2000));
        when(accountTokenRepository.findByToken("abcd")).thenReturn(Optional.empty());
        assertThrows(VerificationTokenUsedException.class, () -> verificationTokenService.validateAccountVerificationToken(accountVerificationToken.getToken()));
    }

    @Test
    public void validateAccountVerificationToken_tokenUsed_throwVerificationTokenUsedException() {
        AccountVerificationToken accountVerificationToken = new AccountVerificationToken();
        when(accountTokenRepository.findByToken("abcd")).thenReturn(Optional.ofNullable(accountVerificationToken));
        assertThrows(VerificationTokenUsedException.class, () -> verificationTokenService.validateAccountVerificationToken(accountVerificationToken.getToken()));
    }

    @Test
    public void validateAccountVerificationToken_tokenValid_returnToken() throws VerificationTokenUsedException, VerificationTokenExpiredException {
        AccountVerificationToken accountVerificationToken = new AccountVerificationToken();
        accountVerificationToken.setToken("abcd");
        accountVerificationToken.setExpirationDate(Instant.now().plusSeconds(2000));
        when(accountTokenRepository.findByToken("abcd")).thenReturn(Optional.of(accountVerificationToken));
        accountVerificationToken.setToken("abcd");
        assertEquals(accountVerificationToken, verificationTokenService.validateAccountVerificationToken(accountVerificationToken.getToken()));

        verify(accountTokenRepository).deleteById(accountVerificationToken.getId());
    }

    @Test
    public void validateEmailVerificationToken_tokenExpired_throwVerificationTokenUsedException() {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setExpirationDate(Instant.now().minusSeconds(2000));
        when(emailTokenRepository.findByToken("abcd")).thenReturn(Optional.empty());
        assertThrows(VerificationTokenUsedException.class, () -> verificationTokenService.validateEmailVerificationToken(emailVerificationToken.getToken()));
    }

    @Test
    public void validateEmailVerificationToken_tokenUsed_throwVerificationTokenUsedException() {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        when(emailTokenRepository.findByToken("abcd")).thenReturn(Optional.ofNullable(emailVerificationToken));
        assertThrows(VerificationTokenUsedException.class, () -> verificationTokenService.validateEmailVerificationToken(emailVerificationToken.getToken()));
    }

    @Test
    public void validateEmailVerificationToken_tokenValid_returnToken() throws VerificationTokenUsedException, VerificationTokenExpiredException {
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken();
        emailVerificationToken.setToken("abcd");
        emailVerificationToken.setExpirationDate(Instant.now().plusSeconds(2000));
        when(emailTokenRepository.findByToken("abcd")).thenReturn(Optional.of(emailVerificationToken));
        emailVerificationToken.setToken("abcd");
        assertEquals(emailVerificationToken, verificationTokenService.validateEmailVerificationToken(emailVerificationToken.getToken()));

        verify(emailTokenRepository).deleteById(emailVerificationToken.getId());
    }

    @Test
    public void validatePasswordVerificationToken_tokenExpired_throwVerificationTokenUsedException() {
        PasswordVerificationToken passwordVerificationToken = new PasswordVerificationToken();
        passwordVerificationToken.setExpirationDate(Instant.now().minusSeconds(2000));
        when(passwordTokenRepository.findByToken("abcd")).thenReturn(Optional.empty());
        assertThrows(VerificationTokenUsedException.class, () -> verificationTokenService.validatePasswordVerificationToken(passwordVerificationToken.getToken()));
    }

    @Test
    public void validatePasswordVerificationToken_tokenUsed_throwVerificationTokenUsedException() {
        PasswordVerificationToken passwordVerificationToken = new PasswordVerificationToken();
        when(passwordTokenRepository.findByToken("abcd")).thenReturn(Optional.ofNullable(passwordVerificationToken));
        assertThrows(VerificationTokenUsedException.class, () -> verificationTokenService.validatePasswordVerificationToken(passwordVerificationToken.getToken()));
    }

    @Test
    public void validatePasswordVerificationToken_tokenValid_returnToken() throws VerificationTokenUsedException, VerificationTokenExpiredException {
        PasswordVerificationToken passwordVerificationToken = new PasswordVerificationToken();
        passwordVerificationToken.setToken("abcd");
        passwordVerificationToken.setExpirationDate(Instant.now().plusSeconds(2000));
        when(passwordTokenRepository.findByToken("abcd")).thenReturn(Optional.of(passwordVerificationToken));
        passwordVerificationToken.setToken("abcd");
        assertEquals(passwordVerificationToken, verificationTokenService.validatePasswordVerificationToken(passwordVerificationToken.getToken()));

        verify(accountTokenRepository).deleteById(passwordVerificationToken.getId());
    }

    @Test
    public void validateOTPToken_tokenExpired_throwVerificationTokenUsedException() {
        AccountVerificationToken accountVerificationToken = new AccountVerificationToken();
        accountVerificationToken.setExpirationDate(Instant.now().minusSeconds(2000));
        when(accountTokenRepository.findByToken("abcd")).thenReturn(Optional.empty());
        assertThrows(VerificationTokenUsedException.class, () -> verificationTokenService.validateAccountVerificationToken(accountVerificationToken.getToken()));
    }

    @Test
    public void validateOTPToken_tokenUsed_throwVerificationTokenUsedException() {
        AccountVerificationToken accountVerificationToken = new AccountVerificationToken();
        when(accountTokenRepository.findByToken("abcd")).thenReturn(Optional.ofNullable(accountVerificationToken));
        assertThrows(VerificationTokenUsedException.class, () -> verificationTokenService.validateAccountVerificationToken(accountVerificationToken.getToken()));
    }

    @Test
    public void validateOTPToken_tokenValid_returnToken() throws VerificationTokenUsedException, VerificationTokenExpiredException {
        AccountVerificationToken accountVerificationToken = new AccountVerificationToken();
        accountVerificationToken.setToken("abcd");
        accountVerificationToken.setExpirationDate(Instant.now().plusSeconds(2000));
        when(accountTokenRepository.findByToken("abcd")).thenReturn(Optional.of(accountVerificationToken));
        accountVerificationToken.setToken("abcd");
        assertEquals(accountVerificationToken, verificationTokenService.validateAccountVerificationToken(accountVerificationToken.getToken()));
    }
}