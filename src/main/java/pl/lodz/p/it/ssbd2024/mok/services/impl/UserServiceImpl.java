package pl.lodz.p.it.ssbd2024.mok.services.impl;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.SemanticException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.AdministratorMessages;
import pl.lodz.p.it.ssbd2024.messages.OptimisticLockExceptionMessages;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.model.tokens.VerificationToken;
import pl.lodz.p.it.ssbd2024.mok.dto.PasswordHolder;
import pl.lodz.p.it.ssbd2024.mok.repositories.*;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2024.mok.services.EmailService;
import pl.lodz.p.it.ssbd2024.util.SignVerifier;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackFor = NotFoundException.class, propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;
    private final OwnerRepository ownerRepository;
    private final AdministratorRepository administratorRepository;
    private final EmailService emailService;
    private final ThemeRepository themeRepository;
    private final VerificationTokenService verificationTokenService;
    private final SignVerifier signVerifier;

    @Value("${app.url}")
    private String appUrl;

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Transactional(rollbackFor = {SemanticException.class, PathElementException.class}, propagation = Propagation.REQUIRES_NEW)
    public Page<User> getAllFiltered(Specification<User> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<User> getAllFiltered(Specification<User> specification) {
        return userRepository.findAll(specification);
    }

    @Override
    @PreAuthorize("permitAll()")
    public User getUserById(UUID id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    @PreAuthorize("permitAll()")
    public User getUserByGoogleId(String googleId) throws NotFoundException {
        return userRepository.findByGoogleId(googleId).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(rollbackFor = {IdenticalFieldValueException.class, TokenGenerationException.class}, propagation = Propagation.REQUIRES_NEW)
    public User createUser(User newUser, PasswordHolder password) throws IdenticalFieldValueException, TokenGenerationException, CreationException {
        String encodedPassword = passwordEncoder.encode(password.password());
        newUser.setPassword(encodedPassword);
        newUser.getOldPasswords().add(newUser.getPassword());
        return createUser(newUser);
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(rollbackFor = {IdenticalFieldValueException.class, TokenGenerationException.class}, propagation = Propagation.MANDATORY)
    public User createUser(User newUser) throws IdenticalFieldValueException, TokenGenerationException, CreationException {
        Tenant newTenant = new Tenant();
        newTenant.setActive(true);
        newTenant.setUser(newUser);

        Tenant tenant;
        try {
            tenant = tenantRepository.saveAndFlush(newTenant);
            String token = verificationTokenService.generateAccountVerificationToken(newUser);

            URI uri = URI.create(appUrl + "/verify/" + token);
            emailService.sendVerifyAccountEmail(newUser.getEmail(), newUser.getFirstName(), uri.toString(), newUser.getLanguage());
            return tenant.getUser();
        } catch (ConstraintViolationException e) {
            String constraintName = e.getConstraintName();
            if (constraintName.equals("users_login_key") || constraintName.equals("personal_data_email_key")) {
                throw new IdenticalFieldValueException(UserExceptionMessages.LOGIN_OR_EMAIL_EXISTS, ErrorCodes.IDENTICAL_LOGIN_OR_EMAIL);
            } else {
                throw new CreationException(UserExceptionMessages.CREATION_FAILED, ErrorCodes.REGISTRATION_ERROR);
            }
        }
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(rollbackFor = {
            VerificationTokenUsedException.class,
            VerificationTokenExpiredException.class,
            NotFoundException.class
    }, propagation = Propagation.REQUIRES_NEW)
    public void verify(String token) throws VerificationTokenUsedException, VerificationTokenExpiredException, NotFoundException {
        VerificationToken verificationToken = verificationTokenService.validateAccountVerificationToken(token);
        User user = userRepository.findById(verificationToken.getUser().getId()).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
        user.setVerified(true);
        userRepository.saveAndFlush(user);
        emailService.sendAccountVerifiedEmail(user.getEmail(), user.getFirstName(), user.getLanguage());
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public User updateUserData(UUID id, User user, String tagValue) throws NotFoundException, ApplicationOptimisticLockException {
        User userToUpdate = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));

        if (!signVerifier.verifySignature(userToUpdate.getId(), userToUpdate.getVersion(), tagValue)) {
            throw new ApplicationOptimisticLockException(OptimisticLockExceptionMessages.USER_ALREADY_MODIFIED_DATA, ErrorCodes.OPTIMISTIC_LOCK);
        }

        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setLanguage(user.getLanguage());
        userToUpdate.setTimezone(user.getTimezone());
        return userRepository.saveAndFlush(userToUpdate);
    }

    @Override
    @Retryable(maxAttempts = 3, retryFor = {OptimisticLockException.class})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void blockUser(UUID id, UUID administratorId) throws NotFoundException, UserAlreadyBlockedException, AdministratorOwnBlockException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));

        if(user.getId().equals(administratorId)) {
            throw new AdministratorOwnBlockException(AdministratorMessages.OWN_ADMINISTRATOR_BLOCK, ErrorCodes.ADMINISTRATOR_OWN_BLOCK);
        }

        if (user.isBlocked()) {
            throw new UserAlreadyBlockedException(UserExceptionMessages.ALREADY_BLOCKED, ErrorCodes.USER_ALREADY_BLOCKED);
        }
        user.setBlocked(true);
        userRepository.saveAndFlush(user);
        emailService.sendAccountBlockEmail(user.getEmail(), user.getFirstName(), user.getLanguage());
    }

    @Override
    @Retryable(maxAttempts = 3, retryFor = {OptimisticLockException.class})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void unblockUser(UUID id) throws NotFoundException, UserAlreadyUnblockedException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
        if (!user.isBlocked()) {
            throw new UserAlreadyUnblockedException(UserExceptionMessages.ALREADY_UNBLOCKED, ErrorCodes.USER_ALREADY_UNBLOCKED);
        }
        user.setBlocked(false);
        userRepository.saveAndFlush(user);
        emailService.sendAccountUnblockEmail(user.getEmail(), user.getFirstName(), user.getLanguage());
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @Transactional(rollbackFor = {IdenticalFieldValueException.class, TokenGenerationException.class}, propagation = Propagation.REQUIRES_NEW)
    public void sendEmailUpdateVerificationEmail(UUID id, String tempEmail) throws NotFoundException, TokenGenerationException, IdenticalFieldValueException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
        if (userRepository.findByEmail(tempEmail).isPresent()){
            throw new IdenticalFieldValueException(UserExceptionMessages.EMAIL_EXISTS, ErrorCodes.IDENTICAL_EMAIL);
        }
        user.setTemporaryEmail(tempEmail);
        userRepository.saveAndFlush(user);
        String token = verificationTokenService.generateEmailVerificationToken(user);
        URI uri = URI.create(appUrl + "/update-email/" + token);
        emailService.sendEmailChangeEmail(tempEmail, user.getFirstName(), uri.toString(), user.getLanguage());
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(rollbackFor = {NotFoundException.class, VerificationTokenUsedException.class, VerificationTokenExpiredException.class, IdenticalFieldValueException.class}, propagation = Propagation.REQUIRES_NEW)
    public void changeUserEmail(String token, PasswordHolder password) throws NotFoundException, VerificationTokenUsedException, VerificationTokenExpiredException, InvalidPasswordException, IdenticalFieldValueException {
        User checkPasswordUser = verificationTokenService.getUserByEmailToken(token);
        if (!passwordEncoder.matches(password.password(), checkPasswordUser.getPassword())) {
            throw new InvalidPasswordException(UserExceptionMessages.INVALID_PASSWORD, ErrorCodes.INVALID_PASSWORD);
        }
        VerificationToken verificationToken = verificationTokenService.validateEmailVerificationToken(token);

        User user = userRepository.findById(verificationToken.getUser().getId()).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
        try {
            user.setEmail(user.getTemporaryEmail());
            user.setTemporaryEmail(null);
            userRepository.saveAndFlush(user);
        }catch (ConstraintViolationException constraintViolationException){
            throw new IdenticalFieldValueException(UserExceptionMessages.EMAIL_EXISTS,constraintViolationException, ErrorCodes.IDENTICAL_EMAIL);
        }
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(rollbackFor = {IdenticalFieldValueException.class, TokenGenerationException.class, UserBlockedException.class, UserNotVerifiedException.class}, propagation = Propagation.REQUIRES_NEW)
    public void sendChangePasswordEmail(String email) throws NotFoundException, TokenGenerationException, UserBlockedException, UserNotVerifiedException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));

        if (user.isBlocked()) {
            throw new UserBlockedException(UserExceptionMessages.BLOCKED, ErrorCodes.USER_BLOCKED);
        }

        if (!user.isVerified()) {
            throw new UserNotVerifiedException(UserExceptionMessages.NOT_VERIFIED, ErrorCodes.USER_NOT_VERIFIED);
        }

        String token = verificationTokenService.generatePasswordVerificationToken(user);

        String link = appUrl + "/reset-password?token=" + token;
        emailService.sendPasswordChangeEmail(user.getEmail(), user.getFirstName(), link, user.getLanguage());
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    @Transactional(rollbackFor = {IdenticalFieldValueException.class, InvalidPasswordException.class}, propagation = Propagation.REQUIRES_NEW)
    public void changePassword(UUID id, PasswordHolder oldPassword, PasswordHolder newPassword) throws NotFoundException, InvalidPasswordException, PasswordRepetitionException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));

        if (!passwordEncoder.matches(oldPassword.password(), user.getPassword())) {
            throw new InvalidPasswordException(UserExceptionMessages.INVALID_PASSWORD, ErrorCodes.INVALID_PASSWORD);
        }
        for (String password : user.getOldPasswords()) {
            if (passwordEncoder.matches(newPassword.password(), password)) {
                throw new PasswordRepetitionException(UserExceptionMessages.PASSWORD_REPEATED, ErrorCodes.PASSWORD_REPETITION);
            }
        }

        user.getOldPasswords().add(user.getPassword());
        user.getOldPasswords().add(newPassword.password());
        user.setPassword(passwordEncoder.encode(newPassword.password()));
        userRepository.saveAndFlush(user);
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(rollbackFor = {VerificationTokenUsedException.class, VerificationTokenExpiredException.class, UserBlockedException.class}, propagation = Propagation.REQUIRES_NEW)
    public void changePasswordWithToken(PasswordHolder newPassword, String token) throws VerificationTokenUsedException, VerificationTokenExpiredException, UserBlockedException, PasswordRepetitionException {
        User user = verificationTokenService.getUserByPasswordToken(token);

        if (user.isBlocked()) {
            throw new UserBlockedException(UserExceptionMessages.BLOCKED, ErrorCodes.USER_BLOCKED);
        }

        for (String password : user.getOldPasswords()) {
            if (passwordEncoder.matches(newPassword.password(), password)) {
                throw new PasswordRepetitionException(UserExceptionMessages.PASSWORD_REPEATED, ErrorCodes.PASSWORD_REPETITION);
            }
        }

        verificationTokenService.validatePasswordVerificationToken(token);

        user.getOldPasswords().add(user.getPassword());
        user.getOldPasswords().add(newPassword.password());
        user.setPassword(passwordEncoder.encode(newPassword.password()));
        userRepository.saveAndFlush(user);
    }

    @Override
    @PreAuthorize("permitAll()")
    @Transactional(rollbackFor = NotFoundException.class, propagation = Propagation.REQUIRED)
    public List<String> getUserRoles(UUID id) {
        List<String> roles = new ArrayList<>();
        administratorRepository.findByUserIdAndActive(id, true).ifPresent(administrator -> roles.add("ADMINISTRATOR"));
        ownerRepository.findByUserIdAndActive(id, true).ifPresent(owner -> roles.add("OWNER"));
        tenantRepository.findByUserIdAndActive(id, true).ifPresent(tenant -> roles.add("TENANT"));

        return roles;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public String changeTheme(UUID id, String theme) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
        Theme themeEnt = themeRepository.findByType(theme).orElseThrow(() -> new NotFoundException(UserExceptionMessages.THEME_NOT_FOUND, ErrorCodes.THEME_NOT_FOUND));
        user.setTheme(themeEnt);
        return userRepository.saveAndFlush(user).getTheme().getType();
    }

    @Override
    @PreAuthorize("permitAll()")
    public void reactivateUser(String token) throws VerificationTokenUsedException, VerificationTokenExpiredException {
        VerificationToken verificationToken = verificationTokenService.validateAccountActivateToken(token);
        User user = userRepository.getReferenceById(verificationToken.getUser().getId());
        user.setActive(true);
        userRepository.saveAndFlush(user);
    }
}