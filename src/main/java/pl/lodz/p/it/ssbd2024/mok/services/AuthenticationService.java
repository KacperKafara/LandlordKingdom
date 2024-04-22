package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationResponse;

import java.util.List;
import java.util.UUID;

public interface AuthenticationService {
    List<String> getUserRoles(User user);

    String authenticate(String login, String password) throws NotFoundException, UserNotVerifiedException, UserBlockedException, InvalidLoginDataException, SignInBlockedException;
}
