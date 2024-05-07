package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.EmailVerificationToken;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.model.VerificationToken;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2024.services.EmailService;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final UserRepository userRepository;


    @Value("${app.url}")
    private String appUrl;


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
    public void createUser(User newUser, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        Tenant newTenant = new Tenant();
        newTenant.setActive(true);
        newTenant.setUser(newUser);
        tenantRepository.saveAndFlush(newTenant);

        String token = verificationTokenService.generateAccountVerificationToken(newUser);

        URI uri = URI.create(appUrl + "/auth/verify/" + token);
        emailService.sendAccountActivationEmail(newUser.getEmail(), newUser.getFirstName(), uri.toString(), newUser.getLanguage());
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
        User user = getUserById(id);

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
    public void resetUserPassword(String login) throws NotFoundException {
        User user = getUserByLogin(login);
        String token = verificationTokenService.generatePasswordVerificationToken(user);

        String link = "http://localhost:3000/reset-password?token=" + token;
        emailService.sendEmail(user.getEmail(), "Change password", link);
    }

    @Override
    public void changePassword(UUID id, String oldPassword, String newPassword) throws NotFoundException, InvalidPasswordException {
        User user = getUserById(id);

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new InvalidPasswordException(UserExceptionMessages.INVALID_PASSWORD);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.saveAndFlush(user);
    }

    @Override
    public void sendUpdateEmail(UUID id) throws NotFoundException {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        String token = verificationTokenService.generateEmailVerificationToken(user);
        URI uri = URI.create(appUrl + "/account/change-email/" + token);
        Map<String, Object> templateModel = Map.of("name", user.getFirstName(), "url", uri);
        emailService.sendHtmlEmail(user.getEmail(), "Email address change", "email", templateModel, "en");
//        emailService.sendEmail(user.getEmail(),"Email address update", "http://localhost:3000/account/change-email/" + token);
    }

    @Override
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
}