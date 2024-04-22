package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.model.User;

import java.util.UUID;

public interface UserService {
    User getUser(UUID id);

    void registerUser(User newUser, String password);

    void blockUser(UUID id);

    void unblockUser(UUID id);
}