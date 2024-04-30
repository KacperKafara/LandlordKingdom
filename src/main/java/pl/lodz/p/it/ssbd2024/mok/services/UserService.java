package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAll();

    User getUserById(UUID id) throws NotFoundException;

    void createUser(User newUser, String password);

    void blockUser(UUID id) throws NotFoundException;

    User updateUserData(UUID id, User user) throws NotFoundException;

    void unblockUser(UUID id) throws NotFoundException;

    public void sendUpdateEmail(UUID id) throws NotFoundException;
    public void changeUserEmail(String token, String email) throws NotFoundException, VerificationTokenUsedException, VerificationTokenExpiredException;

}
