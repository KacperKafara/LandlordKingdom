package pl.lodz.p.it.ssbd2024.mol.dto;

import java.util.UUID;

public record RoleRequestResponse(
        UUID id,
        String login,
        String email,
        String firstName,
        String lastName,
        UUID userId
) {
}
