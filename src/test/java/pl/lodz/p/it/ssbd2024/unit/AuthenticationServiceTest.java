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
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthAdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthOwnerRepository;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthTenantRepository;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthUserRepository;
import pl.lodz.p.it.ssbd2024.mok.dto.PasswordHolder;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.mok.services.impl.JwtService;
import pl.lodz.p.it.ssbd2024.mok.services.EmailService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("unit")
@SpringJUnitConfig({ToolConfig.class, MockConfig.class})
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthUserRepository userRepository;

    @Autowired
    private AuthTenantRepository tenantRepository;

    @Autowired
    private AuthOwnerRepository ownerRepository;

    @Autowired
    private AuthAdministratorRepository administratorRepository;

    @Autowired
    private EmailService emailService;

    @Value("${login.maxAttempts}")
    private int maxLoginAttempts;

    @Value("${login.timeOut}")
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
    public void Authenticate_UserIsNotVerified_ThrowException_Test() {
        user.setVerified(false);

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        assertThrows(UserNotVerifiedException.class, () -> authenticationService.generateOTP(login, new PasswordHolder(password), language, ip));

        verify(userRepository, never()).saveAndFlush(user);
        verify(emailService, never()).sendOTPEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void Authenticate_UserIsBlocked_ThrowException_Test() {
        user.setBlocked(true);

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        assertThrows(UserBlockedException.class, () -> authenticationService.generateOTP(login, new PasswordHolder(password), language, ip));

        verify(userRepository, never()).saveAndFlush(user);
        verify(emailService, never()).sendOTPEmail(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    public void Authenticate_UserReachedMaxLoginAttemptsSoCannotSignIn_ThrowException_Test() {
        user.setLoginAttempts(maxLoginAttempts);
        user.setLastFailedLogin(LocalDateTime.now().minusSeconds(loginTimeOut / 3));

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        assertThrows(SignInBlockedException.class, () -> authenticationService.generateOTP(login, new PasswordHolder(password), language, ip));

        verify(userRepository, never()).saveAndFlush(user);
        verify(emailService, never()).sendOTPEmail(anyString(), anyString(), anyString(), anyString());
    }
}