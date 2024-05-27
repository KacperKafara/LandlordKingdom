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

    List<User> getAllFiltered(Specification<User> specification);

    User getUserById(UUID id) throws NotFoundException;

    User getUserByGoogleId(String googleId) throws NotFoundException;

    User createUser(User newUser, String password) throws IdenticalFieldValueException, TokenGenerationException, CreationException;

    User createUser(User newUser) throws IdenticalFieldValueException, TokenGenerationException, CreationException;

    void verify(String token) throws VerificationTokenUsedException, VerificationTokenExpiredException, NotFoundException;

    User updateUserData(UUID id, User user, String tagValue) throws NotFoundException, ApplicationOptimisticLockException;

    void blockUser(UUID id) throws NotFoundException, UserAlreadyBlockedException;

    void unblockUser(UUID id) throws NotFoundException, UserAlreadyUnblockedException;

    void sendEmailUpdateVerificationEmail(UUID id, String tempEmail) throws NotFoundException, TokenGenerationException;

    void changeUserEmail(String token, String password) throws NotFoundException, VerificationTokenUsedException, VerificationTokenExpiredException, InvalidPasswordException;

    void sendChangePasswordEmail(String login) throws NotFoundException, TokenGenerationException, UserBlockedException, UserNotVerifiedException;

    void changePassword(UUID id, String oldPassword, String newPassword) throws NotFoundException, InvalidPasswordException, PasswordRepetitionException;

    void changePasswordWithToken(String password, String token) throws VerificationTokenUsedException, VerificationTokenExpiredException, UserBlockedException, PasswordRepetitionException;

    List<String> getUserRoles(UUID id);

    String changeTheme(UUID id, String theme) throws NotFoundException;
}
