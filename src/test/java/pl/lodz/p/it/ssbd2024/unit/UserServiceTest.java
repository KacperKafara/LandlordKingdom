package pl.lodz.p.it.ssbd2024.unit;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.lodz.p.it.ssbd2024.config.ToolConfig;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.mok.repositories.EmailVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.PasswordVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;
import pl.lodz.p.it.ssbd2024.util.SignVerifier;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ActiveProfiles("unit")
@SpringJUnitConfig({ToolConfig.class, MockConfig.class})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private SignVerifier signVerifier;

    @Autowired
    private EmailVerificationTokenRepository emailVerificationTokenRepository;

    @Autowired
    private PasswordVerificationTokenRepository passwordVerificationTokenRepository;

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
    @DisplayName("Block user - user is blocked")
    void BlockUser_UserIsBlocked_ThrowException_Test() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setBlocked(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        user.setBlocked(true);
        assertThrows(UserAlreadyBlockedException.class, () -> userService.blockUser(userId));
    }

    @Test
    @DisplayName("Block user - user is blocked")
    void UnblockUser_UserIsNotBlocked_ThrowException_Test() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setBlocked(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        user.setBlocked(false);
        assertThrows(UserAlreadyUnblockedException.class, () -> userService.unblockUser(userId));
    }

    @Test
    @DisplayName("Block user - user is not blocked")
    void BlockUser_UserIsNotBlocked_Test() throws NotFoundException {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setBlocked(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.blockUser(userId);
        Assertions.assertTrue(user.isBlocked());
    }

    @Test
    @DisplayName("Unblock user - user is blocked")
    void UnblockUser_UserIsBlocked_Test() throws NotFoundException {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setBlocked(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        userService.unblockUser(userId);
        Assertions.assertFalse(user.isBlocked());
    }


    @Test
    @DisplayName("Create user - identical login")
    void CreateUser_Identical_Login_Test() {
        User user = new User(firstName, lastName, email, login);
        doThrow(new ConstraintViolationException("", null, "users_login_key")).when(tenantRepository).saveAndFlush(any());
        assertThrows(IdenticalFieldValueException.class, () -> userService.createUser(user, password));

    }

    @Test
    @DisplayName("Create user - identical email")
    void CreateUser_Identical_Email_Test() {
        User user = new User(firstName, lastName, email, login);
        Tenant tenant = new Tenant();
        tenant.setUser(user);
        doThrow(new ConstraintViolationException("", null, "personal_data_email_key")).when(tenantRepository).saveAndFlush(any());
        assertThrows(IdenticalFieldValueException.class, () -> userService.createUser(user, password));
    }

    @Test
    @DisplayName("Update user - user already modified")
    void UpdateUserData_UserAlreadyModified_Test() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(signVerifier.verifySignature(any(), any(), eq("tagValue"))).thenReturn(true);
        assertThrows(ApplicationOptimisticLockException.class, () -> userService.updateUserData(userId, user, "tagValue"));
    }

    @Test
    @DisplayName("Change user email - verification token expired")
    void ChangeUserEmail_VerificationTokenExpired_Test() {
        User user = new User();
        when(emailVerificationTokenRepository.findByToken("tagValue")).thenReturn(Optional.of(new EmailVerificationToken("tagValue", Instant.now().minusSeconds(60), user)));
        assertThrows(VerificationTokenExpiredException.class, () -> userService.changeUserEmail("tagValue", "new@mail.com"));
    }

    @Test
    @DisplayName("Change user password - invalid password")
    void ChangPassword_Invalid_Password_Test() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setPassword(encoded);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(InvalidPasswordException.class, () -> userService.changePassword(userId, "oldPasswd", "newPassword"));
    }

    @Test
    @DisplayName("Change password with token - verification token expired")
    void ChangePasswordWithToken_VerificationTokenExpired_Test() {
        User user = new User();
        when(passwordVerificationTokenRepository.findByToken("token")).thenReturn(Optional.of(new PasswordVerificationToken("token", Instant.now().minusSeconds(60), user)));
        assertThrows(VerificationTokenExpiredException.class, () -> userService.changePasswordWithToken("password", "token"));
    }

}