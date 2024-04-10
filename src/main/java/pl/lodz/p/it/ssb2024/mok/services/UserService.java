package pl.lodz.p.it.ssb2024.mok.services;

import pl.lodz.p.it.ssb2024.model.User;

import java.util.UUID;

public interface UserService {
    User getUser(UUID id) throws Exception;
    void registerUser(User newUser);
}
