package pl.lodz.p.it.ssb2024.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssb2024.model.User;
import pl.lodz.p.it.ssb2024.mok.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(UUID id) throws Exception {
        Optional<User> retValue = repository.findById(id);
        if (retValue.isEmpty()){
            throw new Exception("no user");
        }
        return retValue.get();
    }
}
