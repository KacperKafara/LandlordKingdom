package pl.lodz.p.it.ssbd2024.mol.dto;

import pl.lodz.p.it.ssbd2024.model.LocalState;

import java.util.UUID;

public record EditLocalRequestAdmin(
        UUID id,
        String name,
        String description,
        int size,
        String state
) {
}
