package pl.lodz.p.it.ssbd2024.unit;

import org.hibernate.exception.ConstraintViolationException;
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
import pl.lodz.p.it.ssbd2024.mok.services.EmailService;
import pl.lodz.p.it.ssbd2024.util.SignVerifier;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    private EmailService emailService;

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

        assertThrows(UserAlreadyBlockedException.class, () -> userService.blockUser(userId));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Unblock user - user is not blocked")
    void UnblockUser_UserIsNotBlocked_ThrowException_Test() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setBlocked(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        user.setBlocked(false);
        assertThrows(UserAlreadyUnblockedException.class, () -> userService.unblockUser(userId));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Block user - user is not blocked")
    void BlockUser_UserIsNotBlocked_Test() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setBlocked(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.blockUser(userId));
        Assertions.assertTrue(user.isBlocked());

        verify(userRepository).saveAndFlush(user);
    }

    @Test
    @DisplayName("Unblock user - user is blocked")
    void UnblockUser_UserIsBlocked_Test() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setBlocked(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.unblockUser(userId));
        Assertions.assertFalse(user.isBlocked());

        verify(userRepository).saveAndFlush(user);
    }


    @Test
    @DisplayName("Create user - identical login")
    void CreateUser_Identical_Login_Test() {
        User user = new User(firstName, lastName, email, login);
        doThrow(new ConstraintViolationException("", null, "personal_data_email_key")).when(tenantRepository).saveAndFlush(any());
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
        when(signVerifier.verifySignature(any(), any(), eq("tagValue"))).thenReturn(false);
        assertThrows(ApplicationOptimisticLockException.class, () -> userService.updateUserData(userId, user, "tagValue"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Change user email - verification token expired")
    void ChangeUserEmail_VerificationTokenExpired_Test() {
        User user = new User();
        when(emailVerificationTokenRepository.findByToken("tagValue")).thenReturn(Optional.of(new EmailVerificationToken("tagValue", Instant.now().minusSeconds(60), user)));
        assertThrows(VerificationTokenExpiredException.class, () -> userService.changeUserEmail("tagValue", "new@mail.com"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Change user password - invalid password")
    void ChangPassword_Invalid_Password_Test() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setPassword(encoded);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(InvalidPasswordException.class, () -> userService.changePassword(userId, "oldPasswd", "newPassword"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Change password with token - verification token expired")
    void ChangePasswordWithToken_VerificationTokenExpired_Test() {
        User user = new User();
        when(passwordVerificationTokenRepository.findByToken("token")).thenReturn(Optional.of(new PasswordVerificationToken("token", Instant.now().minusSeconds(60), user)));
        assertThrows(VerificationTokenExpiredException.class, () -> userService.changePasswordWithToken("password", "token"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Change password with token - user is blocked")
    void ChangePasswordWithToken_UserIsBlocked_Test() {
        User user = new User();
        user.setBlocked(true);
        when(passwordVerificationTokenRepository.findByToken("token")).thenReturn(Optional.of(new PasswordVerificationToken("token", Instant.now().plusSeconds(60), user)));
        assertThrows(UserBlockedException.class, () -> userService.changePasswordWithToken("password", "token"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Send change password email - user is blocked")
    void SendChangePasswordEmail_UserIsBlocked_Test() {
        User user = new User();
        user.setBlocked(true);
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));
        assertThrows(UserBlockedException.class, () -> userService.sendChangePasswordEmail("email"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Send change password email - user is not verified")
    void SendChangePasswordEmail_UserIsNotVerified_Test() {
        User user = new User();
        user.setVerified(false);
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));
        assertThrows(UserNotVerifiedException.class, () -> userService.sendChangePasswordEmail("email"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Get user by login - user found")
    void getUserByLogin_UserFound_ReturnUser() throws NotFoundException {
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));

        User result = userService.getUserByLogin(login);

        Assertions.assertEquals(user, result);
    }

    @Test
    @DisplayName("Get user by login - user not found")
    void getUserByLogin_UserNotFound_ThrowNotFoundException() {
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUserByLogin(login));
    }

    @Test
    @DisplayName("Get all users")
    void getAll_ReturnListOfUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAll();

        Assertions.assertEquals(users, result);
    }

    @Test
    @DisplayName("Send email update email - user not found")
    void sendEmailUpdateEmail_UserNotFound_ThrowNotFoundException() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.sendEmailUpdateEmail(userId));
    }
}