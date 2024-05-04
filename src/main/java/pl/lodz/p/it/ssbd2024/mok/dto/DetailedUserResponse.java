package pl.lodz.p.it.ssbd2024.mok.dto;

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
        boolean blocked,
        boolean verified
) {
}
