package pl.lodz.p.it.ssbd2024.mok.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.lodz.p.it.ssbd2024.exceptions.InvalidPasswordException;
import pl.lodz.p.it.ssbd2024.exceptions.IdenticalFieldValueException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.TokenGenerationException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenExpiredException;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAll();

    Page<User> getAllFiltered(Specification<User> specification, Pageable pageable);

    User getUserById(UUID id) throws NotFoundException;

    User getUserByEmail(String email) throws NotFoundException;

    User getUserByGoogleId(String googleId) throws NotFoundException;

    User createUser(User newUser, String password) throws IdenticalFieldValueException, TokenGenerationException, CreationException;

    User createUser(User newUser) throws IdenticalFieldValueException, TokenGenerationException, CreationException;

    User getUserByLogin(String login) throws NotFoundException;

    User updateUserData(UUID id, User user, String tagValue) throws NotFoundException, ApplicationOptimisticLockException;

    void blockUser(UUID id) throws NotFoundException, UserAlreadyBlockedException;

    void unblockUser(UUID id) throws NotFoundException, UserAlreadyUnblockedException;

    void sendEmailUpdateEmail(UUID id) throws NotFoundException, TokenGenerationException;

    void changeUserEmail(String token, String email) throws NotFoundException, VerificationTokenUsedException, VerificationTokenExpiredException;

    void sendChangePasswordEmail(String login) throws NotFoundException, TokenGenerationException, UserBlockedException, UserNotVerifiedException;

    void changePassword(UUID id, String oldPassword, String newPassword) throws NotFoundException, InvalidPasswordException;

    void changePasswordWithToken(String password, String token) throws VerificationTokenUsedException, VerificationTokenExpiredException, UserBlockedException;

    void deleteNonVerifiedUsers();

    void sendEmailVerifyAccount();

    List<String> getUserRoles(UUID id);
}
