package pl.lodz.p.it.ssb2024.mok.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssb2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssb2024.exceptions.UserAlreadyBlockedException;
import pl.lodz.p.it.ssb2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssb2024.model.User;
import pl.lodz.p.it.ssb2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssb2024.mok.services.UserService;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${login_max_attempts:3}")
    private int maxLoginAttempts;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUser(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
    }

    @Override
    public void registerUser(User newUser) {
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        repository.saveAndFlush(newUser);
    }

    @Override
    public void blockUser(UUID id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        if (user.isBlocked() && user.getLoginAttempts() < maxLoginAttempts) {
            throw new UserAlreadyBlockedException(UserExceptionMessages.ALREADY_BLOCKED);
        }

        user.setBlocked(true);
        user.setLoginAttempts(0);
        repository.saveAndFlush(user);
    }

}