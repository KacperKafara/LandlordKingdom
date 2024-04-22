package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.UserAlreadyBlockedException;
import pl.lodz.p.it.ssbd2024.exceptions.UserAlreadyUnblockedException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;

    @Override
    public User getUserById(UUID id) throws NotFoundException {
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
    public void blockUser(UUID id) throws NotFoundException {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        if (user.isBlocked()) {
            throw new UserAlreadyBlockedException(UserExceptionMessages.ALREADY_BLOCKED);
        }

        user.setBlocked(true);
        repository.saveAndFlush(user);
    }

    @Override
    public void unblockUser(UUID id) throws NotFoundException {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        if (!user.isBlocked()) {
            throw new UserAlreadyUnblockedException(UserExceptionMessages.ALREADY_UNBLOCKED);
        }

        user.setBlocked(false);
        repository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public User updateUserData(UUID id, User user) throws NotFoundException {
        User userToUpdate = repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        return repository.saveAndFlush(userToUpdate);
    }
}