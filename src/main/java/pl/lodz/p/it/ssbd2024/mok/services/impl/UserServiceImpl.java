package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.AccountVerificationToken;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.model.VerificationToken;
import pl.lodz.p.it.ssbd2024.mok.repositories.AccountVerificationTokenRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2024.services.EmailService;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackFor = NotFoundException.class)
@RequiredArgsConstructor
@Log
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final AccountVerificationTokenRepository accountVerificationTokenRepository;
    private final UserRepository userRepository;


    @Value("${app.url}")
    private String appUrl;

    @Value("${account.removeUnverifiedAccountAfterHours:24}")
    private int removeUnverifiedAccountsAfterHours;


    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getUserById(UUID id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
    }

    @Override
    public User getUserByLogin(String login) throws NotFoundException {
        return repository.findByLogin(login).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = {IdenticalFieldValueException.class, TokenGenerationException.class})
    public void createUser(User newUser, String password) throws IdenticalFieldValueException, TokenGenerationException {
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        Tenant newTenant = new Tenant();
        newTenant.setActive(true);
        newTenant.setUser(newUser);

        try {
            tenantRepository.saveAndFlush(newTenant);
            String token = verificationTokenService.generateAccountVerificationToken(newUser);

            URI uri = URI.create(appUrl + "/verify/" + token);
            emailService.sendAccountActivationEmail(newUser.getEmail(), newUser.getFirstName(), uri.toString(), newUser.getLanguage());
        } catch (ConstraintViolationException e) {
            String constraintName = e.getConstraintName();
            if (constraintName.equals("users_login_key") || constraintName.equals("personal_data_email_key")) {
                throw new IdenticalFieldValueException(UserExceptionMessages.LOGIN_OR_EMAIL_EXISTS, "login_email");
            }
        }
    }

    @Override
    public User updateUserData(UUID id, User user) throws NotFoundException {
        User userToUpdate = repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setLanguage(user.getLanguage());
        return repository.saveAndFlush(userToUpdate);
    }

    @Override
    public void blockUser(UUID id) throws NotFoundException {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        user.setBlocked(true);
        repository.saveAndFlush(user);
    }

    @Override
    public void unblockUser(UUID id) throws NotFoundException {
        User user = getUserById(id);

        user.setBlocked(false);
        repository.saveAndFlush(user);
    }

    @Override
    @Transactional(rollbackFor = {IdenticalFieldValueException.class, TokenGenerationException.class})
    public void resetUserPassword(String email) throws NotFoundException, TokenGenerationException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        String token = verificationTokenService.generatePasswordVerificationToken(user);

        String link = appUrl + "/reset-password?token=" + token;
        emailService.sendPasswordChangeEmail(user.getEmail(), user.getFirstName(), link, user.getLanguage());
    }

    @Override
    @Transactional(rollbackFor = {IdenticalFieldValueException.class, InvalidPasswordException.class})
    public void changePassword(UUID id, String oldPassword, String newPassword) throws NotFoundException, InvalidPasswordException {
        User user = getUserById(id);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidPasswordException(UserExceptionMessages.INVALID_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(rollbackFor = {IdenticalFieldValueException.class, TokenGenerationException.class})
    public void sendEmailUpdateEmail(UUID id) throws NotFoundException, TokenGenerationException {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        user.setBlocked(false);
        String token = verificationTokenService.generateEmailVerificationToken(user);
        URI uri = URI.create(appUrl + "/update-email/" + token);
        emailService.sendEmailChangeEmail(user.getEmail(), user.getFirstName(), uri.toString(), user.getLanguage());
    }

    @Override
    @Transactional(rollbackFor = {NotFoundException.class, VerificationTokenUsedException.class, VerificationTokenExpiredException.class})
    public void changeUserEmail(String token, String email) throws NotFoundException, VerificationTokenUsedException, VerificationTokenExpiredException {
        VerificationToken verificationToken = verificationTokenService.validateEmailVerificationToken(token);
        User user = repository.findById(verificationToken.getUser().getId()).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        user.setEmail(email);
        repository.saveAndFlush(user);

    }

    @Override
    public void changePasswordWithToken(String password, String token) throws VerificationTokenUsedException, VerificationTokenExpiredException {
        VerificationToken verificationToken = verificationTokenService.validatePasswordVerificationToken(token);
        User user = verificationToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteNonVerifiedUsers() {
        LocalDateTime beforeTime = LocalDateTime.now().minusHours(removeUnverifiedAccountsAfterHours);
        List<User> users = repository.getUsersByCreatedAtBeforeAndVerifiedIsFalse(beforeTime);
        users.forEach(user -> {
            emailService.sendAccountDeletedEmail(user.getEmail(), user.getFirstName(), user.getLanguage());
            repository.delete(user);
            repository.flush();
        });
    }

    @Override
    public void sendEmailVerifyAccount() {
        LocalDateTime beforeTime = LocalDateTime.now().minusHours(removeUnverifiedAccountsAfterHours / 2);
        LocalDateTime afterTime = beforeTime.plusMinutes(10);
        List<User> users = repository.getUsersByCreatedAtBeforeAndCreatedAtAfterAndVerifiedIsFalse(beforeTime, afterTime);
        users.forEach(user -> {
            Optional<AccountVerificationToken> token = accountVerificationTokenRepository.findByUserId(user.getId());
            if (token.isEmpty()) {
                return;
            }
            URI uri = URI.create(appUrl + "/verify/" + token);
            emailService.sendAccountActivationEmail(user.getEmail(), user.getFirstName(), uri.toString(), user.getLanguage());
        });
    }
}