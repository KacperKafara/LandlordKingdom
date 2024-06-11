package pl.lodz.p.it.ssbd2024.mol.dto;

import jakarta.validation.constraints.NotBlank;
import pl.lodz.p.it.ssbd2024.util.ValidDate;

public record RentPaymentsRequest(
        @NotBlank
        @ValidDate
        String startDate,

        @NotBlank
        @ValidDate
        String endDate
) {
}
