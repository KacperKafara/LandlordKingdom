package pl.lodz.p.it.ssbd2024.mol.dto;

import jakarta.validation.constraints.NotBlank;
import pl.lodz.p.it.ssbd2024.util.ValidDate;

public record PaymentsAndFeesRequest(
        @NotBlank(message = "Start date cannot be blank.")
        @ValidDate(message = "Start date must be a valid date.")
        String startDate,

        @NotBlank(message = "End date cannot be blank.")
        @ValidDate(message = "End date must be a valid date.")
        String endDate
) {
}