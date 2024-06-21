package pl.lodz.p.it.ssbd2024.unit;

import jakarta.persistence.OptimisticLockException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.lodz.p.it.ssbd2024.config.ToolConfig;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.model.tokens.EmailVerificationToken;
import pl.lodz.p.it.ssbd2024.model.tokens.PasswordVerificationToken;
import pl.lodz.p.it.ssbd2024.mok.dto.PasswordHolder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @DisplayName("Unblock user - user is not blocked")
    @Order(2)
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
    @DisplayName("Unblock user - user is blocked")
    @Order(4)
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
    @Order(5)
    void CreateUser_Identical_Login_Test() {
        User user = new User(firstName, lastName, email, login);
        doThrow(new ConstraintViolationException("", null, "personal_data_email_key")).when(tenantRepository).saveAndFlush(any());
        assertThrows(IdenticalFieldValueException.class, () -> userService.createUser(user, new PasswordHolder(password)));
    }

    @Test
    @DisplayName("Create user - identical email")
    @Order(6)
    void CreateUser_Identical_Email_Test() {
        User user = new User(firstName, lastName, email, login);
        Tenant tenant = new Tenant();
        tenant.setUser(user);
        doThrow(new ConstraintViolationException("", null, "personal_data_email_key")).when(tenantRepository).saveAndFlush(any());
        assertThrows(IdenticalFieldValueException.class, () -> userService.createUser(user, new PasswordHolder(password)));
    }

    @Test
    @DisplayName("Update user - user already modified")
    @Order(7)
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
    @Order(8)
    void ChangeUserEmail_VerificationTokenExpired_Test() {
        User user = new User();
        user.setPassword("$2a$12$bOPVAvWOC2f9gJoF37IeE.N9Ij15GfWeVlvHzDPTOJk66NimJMJ4.");
        when(emailVerificationTokenRepository.findByToken("tagValue")).thenReturn(Optional.of(new EmailVerificationToken("tagValue", Instant.now().minusSeconds(60), user)));

        assertThrows(VerificationTokenExpiredException.class, () -> userService.changeUserEmail("tagValue", new PasswordHolder("password")));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Change user password - invalid password")
    @Order(9)
    void ChangPassword_Invalid_Password_Test() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setPassword(encoded);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(InvalidPasswordException.class, () -> userService.changePassword(userId, new PasswordHolder("oldPasswd"), new PasswordHolder("newPassword")));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Change user password - password repetition")
    void ChangPassword_PasswordRepetition_ThrowException() {
        UUID userId = UUID.randomUUID();
        String encoded2 = "$2a$12$yXC7QQeg3WVOGCvR/MxiAO8pSx2cqX5BJSFhZ/A2D2FyAwb0rRYhK";
        User user = new User();
        user.setPassword(encoded);
        user.getOldPasswords().add(encoded2);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertThrows(PasswordRepetitionException.class, () -> userService.changePassword(userId, new PasswordHolder(password), new PasswordHolder("qwerasdf")));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Change password with token - verification token expired")
    @Order(10)
    void ChangePasswordWithToken_VerificationTokenExpired_Test() {
        User user = new User();
        when(passwordVerificationTokenRepository.findByToken("token")).thenReturn(Optional.of(new PasswordVerificationToken("token", Instant.now().minusSeconds(60), user)));
        assertThrows(VerificationTokenExpiredException.class, () -> userService.changePasswordWithToken(new PasswordHolder("password"), "token"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Change password with token - user is blocked")
    @Order(11)
    void ChangePasswordWithToken_UserIsBlocked_Test() {
        User user = new User();
        user.setBlocked(true);
        when(passwordVerificationTokenRepository.findByToken("token")).thenReturn(Optional.of(new PasswordVerificationToken("token", Instant.now().plusSeconds(60), user)));
        assertThrows(UserBlockedException.class, () -> userService.changePasswordWithToken(new PasswordHolder("password"), "token"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Send change password email - user is blocked")
    @Order(12)
    void SendChangePasswordEmail_UserIsBlocked_Test() {
        User user = new User();
        user.setBlocked(true);
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));
        assertThrows(UserBlockedException.class, () -> userService.sendChangePasswordEmail("email"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Send change password email - user is not verified")
    @Order(13)
    void SendChangePasswordEmail_UserIsNotVerified_Test() {
        User user = new User();
        user.setVerified(false);
        when(userRepository.findByEmail("email")).thenReturn(Optional.of(user));
        assertThrows(UserNotVerifiedException.class, () -> userService.sendChangePasswordEmail("email"));

        verify(userRepository, never()).saveAndFlush(user);
    }

    @Test
    @DisplayName("Send email update email - user not found")
    @Order(15)
    void sendEmailUpdateVerificationEmail_UserNotFound_ThrowNotFoundException() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.sendEmailUpdateVerificationEmail(userId, "temp@mail.com" ));
    }

    @Test
    @DisplayName("Unblock user - should retry")
    @Order(17)
    void unblockUser_throwsOptimisticLockException_shouldRetry() throws NotFoundException, UserAlreadyUnblockedException {
        reset(userRepository, emailService);
        UUID userId = UUID.randomUUID();
        User user = new User();
        User user2 = new User();
        user2.setEmail("");
        user2.setFirstName("");
        user2.setLanguage("pl");
        user2.setBlocked(true);
        user.setBlocked(true);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.of(user2));
        when(userRepository.saveAndFlush(any()))
                .thenThrow(OptimisticLockException.class)
                .thenReturn(user2);
        userService.unblockUser(userId);

        verify(userRepository, times(2)).saveAndFlush(any());
        verify(emailService, times(1)).sendAccountUnblockEmail(user2.getEmail(), user2.getFirstName(), user2.getLanguage());
    }
}