package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.model.User;
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
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;

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
    public void createUser(User newUser, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        Tenant newTenant = new Tenant();
        newTenant.setActive(true);
        newTenant.setUser(newUser);
        tenantRepository.saveAndFlush(newTenant);

        String token = verificationTokenService.generateAccountVerificationToken(newUser);

        URI uri = URI.create(appUrl + "/auth/verify/" + token);
        Map<String, Object> templateModel = Map.of("name", newUser.getFirstName(), "url", uri);
        emailService.sendHtmlEmail(newUser.getEmail(), "Rejestracja", "register", templateModel);
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
    @Transactional
    public User updateUserData(UUID id, User user) throws NotFoundException {
        User userToUpdate = getUserById(id);
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        return repository.saveAndFlush(userToUpdate);
    }
}