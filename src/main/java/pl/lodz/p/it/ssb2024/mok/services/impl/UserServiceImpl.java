package pl.lodz.p.it.ssb2024.mok.services.impl;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssb2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssb2024.exceptions.UserAlreadyBlockedException;
import pl.lodz.p.it.ssb2024.exceptions.UserAlreadyUnblockedException;
import pl.lodz.p.it.ssb2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssb2024.model.Tenant;
import pl.lodz.p.it.ssb2024.model.User;
import pl.lodz.p.it.ssb2024.mok.dto.AuthenticationRequest;
import pl.lodz.p.it.ssb2024.mok.dto.AuthenticationResponse;
import pl.lodz.p.it.ssb2024.mok.repositories.AdministratorRepository;
import pl.lodz.p.it.ssb2024.mok.repositories.OwnerRepository;
import pl.lodz.p.it.ssb2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssb2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssb2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssb2024.mok.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${login_max_attempts:3}")
    private int maxLoginAttempts;
    private final TenantRepository tenantRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder,
                           TenantRepository tenantRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tenantRepository = tenantRepository;
    }

    @Override
    public User getUser(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = ConstraintViolationException.class)
    public void registerUser(User newUser, String password) {
        try {
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
            Tenant newTenant = new Tenant();
            newTenant.setActive(true);
            newTenant.setUser(newUser);
            tenantRepository.saveAndFlush(newTenant);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void blockUser(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        if (user.isBlocked()) {
            throw new UserAlreadyBlockedException(UserExceptionMessages.ALREADY_BLOCKED);
        }

        user.setBlocked(true);
        repository.saveAndFlush(user);
    }

    @Override
    public void unblockUser(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        if (!user.isBlocked()) {
            throw new UserAlreadyUnblockedException(UserExceptionMessages.ALREADY_UNBLOCKED);
        }

        user.setBlocked(false);
        repository.saveAndFlush(user);
    }

    public String test() {
        return "test";
    }
}