package pl.lodz.p.it.ssbd2024.mol.dto;

public record PaymentsAndFeesRequest(
        @NotBlank
        @ValidDate
import jakarta.validation.constraints.NotBlank;
import pl.lodz.p.it.ssbd2024.util.ValidDate;
        String startDate,

        @NotBlank
        @ValidDate
        String endDate
) {
}