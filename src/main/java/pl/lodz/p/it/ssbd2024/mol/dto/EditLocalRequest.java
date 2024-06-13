package pl.lodz.p.it.ssbd2024.mol.dto;

import java.util.UUID;

public record EditLocalRequest(
        UUID id,
        String name,
        String description,
        String state
) {
}
