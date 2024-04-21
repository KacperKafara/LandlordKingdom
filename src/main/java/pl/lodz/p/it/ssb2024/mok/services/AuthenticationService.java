package pl.lodz.p.it.ssb2024.mok.services;

import pl.lodz.p.it.ssb2024.mok.dto.AuthenticationResponse;

import java.util.List;
import java.util.UUID;

public interface AuthenticationService {
    List<String> getUserRoles(UUID userId, boolean active);

    AuthenticationResponse authenticate(String login, String password);
}
