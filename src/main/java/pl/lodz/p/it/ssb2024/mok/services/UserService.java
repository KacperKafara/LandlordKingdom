package pl.lodz.p.it.ssb2024.mok.services;

import pl.lodz.p.it.ssb2024.model.Administrator;
import pl.lodz.p.it.ssb2024.model.Owner;
import pl.lodz.p.it.ssb2024.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUser(UUID id);

    void registerUser(User newUser, String password);

    void blockUser(UUID id);

    void unblockUser(UUID id);
}
