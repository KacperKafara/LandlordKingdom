package pl.lodz.p.it.ssbd2024.mol.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import pl.lodz.p.it.ssbd2024.util.ValidDate;

public record SetEndDateRequest(

        @ValidDate
        @NotBlank
        String newEndDate
) {
}
