package pl.lodz.p.it.ssbd2024.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.lodz.p.it.ssbd2024.config.ToolConfig;
import pl.lodz.p.it.ssbd2024.exceptions.SignInBlockedException;
import pl.lodz.p.it.ssbd2024.exceptions.UserBlockedException;
import pl.lodz.p.it.ssbd2024.exceptions.UserNotVerifiedException;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.repositories.AdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.OwnerRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.mok.services.impl.JwtService;
import pl.lodz.p.it.ssbd2024.services.EmailService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("unit")
@SpringJUnitConfig({ToolConfig.class, MockConfig.class})
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private EmailService emailService;

    @Value("${login_max_attempts}")
    private int maxLoginAttempts;

    @Value("${login_time_out}")
    private int loginTimeOut;

    String login;
    String password;
    String encoded;
    String firstName;
    String lastName;
    String email;
    User user;
    String ip;
    String language;

    @BeforeEach
    public void initData() {
        login = "testLogin";
        password = "P@ssw0rd!";
        encoded = "$2a$12$1PxOGUtIHJueA7kxtLegDeoO5PUwRSQIAcrjWRmvRH3Gs6zQtdb/q";
        firstName = "FirstName";
        lastName = "LastName";
        email = "test@mail.com";
        language = "en";
        user = new User(firstName, lastName, email, login);
        user.setPassword(encoded);
        user.setVerified(true);
        user.setBlocked(false);
        user.setLoginAttempts(0);
        user.setLanguage(language);
        user.setLastFailedLogin(LocalDateTime.now().minusMonths(5));
        user.setLastSuccessfulLogin(LocalDateTime.now().minusDays(3));
        ip = "1.1.1.1";
    }

    @Test
    public void Authenticate_CredentialsCorrect_ReturnToken_Test() {
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> authenticationService.generateOTP(login, password, language, ip));
    }

    @Test
    public void Authenticate_UserIsNotVerified_ThrowException_Test() {
        user.setVerified(false);

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        assertThrows(UserNotVerifiedException.class, () -> authenticationService.generateOTP(login, password, language, ip));
    }

    @Test
    public void Authenticate_UserIsBlocked_ThrowException_Test() {
        user.setBlocked(true);

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        assertThrows(UserBlockedException.class, () -> authenticationService.generateOTP(login, password, language, ip));
    }

    @Test
    public void Authenticate_UserReachedMaxLoginAttemptsSoCannotSignIn_ThrowException_Test() {
        user.setLoginAttempts(maxLoginAttempts);
        user.setLastFailedLogin(LocalDateTime.now().minusSeconds(loginTimeOut / 3));

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        assertThrows(SignInBlockedException.class, () -> authenticationService.generateOTP(login, password, language, ip));
    }

    @Test
    public void Authenticate_UserReachedMaxLoginAttemptsButTimePassed_ReturnToken_Test() {
        user.setLoginAttempts(3);
        user.setLastFailedLogin(LocalDateTime.now().minusSeconds(loginTimeOut * 10L));

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> authenticationService.generateOTP(login, password, language, ip));
    }
}
