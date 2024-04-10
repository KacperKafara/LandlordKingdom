package pl.lodz.p.it.ssb2024.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssb2024.model.User;
import pl.lodz.p.it.ssb2024.mok.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUser(UUID id) throws Exception {
        Optional<User> retValue = repository.findById(id);
        if (retValue.isEmpty()){
            throw new Exception("no user");
        }
        return retValue.get();
    }

    public void registerUser(String firstName,
                             String lastName,
                             String email,
                             String login,
                             String password) throws Exception {
        Optional<User> existingUserByEmail = repository.findByEmail(email);
        if (existingUserByEmail.isPresent()) {
            throw new Exception("User with the given email already exists");
        }
        Optional<User> existingUserByLogin = repository.findByLogin(login);
        if (existingUserByLogin.isPresent()) {
            throw new Exception("User with the given login already exists");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(firstName, lastName, email, login, encodedPassword, 0, null, null, false, false);
        repository.saveAndFlush(newUser);
    }

}
