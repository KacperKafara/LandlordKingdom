package pl.lodz.p.it.ssbd2024.mol.dto;

import jakarta.validation.constraints.Future;

public record SetEndDateRequest(
        String newEndDate
) {
}
