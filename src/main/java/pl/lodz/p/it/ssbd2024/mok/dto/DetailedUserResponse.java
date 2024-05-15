package pl.lodz.p.it.ssbd2024.mok.dto;

import java.util.List;
import java.util.UUID;

public record DetailedUserResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String login,
        String language,
        String lastSuccessfulLogin,
        String lastFailedLogin,
        String lastSuccessfulLoginIP,
        String lastFailedLoginIP,
        boolean blocked,
        boolean verified,
        List<String> roles
) {
}
