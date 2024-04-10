package pl.lodz.p.it.ssb2024.mok.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssb2024.model.User;
import pl.lodz.p.it.ssb2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssb2024.mok.services.UserService;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(UUID id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public void registerUser(User newUser) {
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        repository.saveAndFlush(newUser);
    }

}
