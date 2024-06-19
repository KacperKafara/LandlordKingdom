package pl.lodz.p.it.ssbd2024.mol.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pl.lodz.p.it.ssbd2024.util.ValidDate;

public record AcceptApplicationRequest(
        @NotNull(message = "End date cannot be null")
        @Size(min = 10, max = 10, message = "End date length has to be 10, pattern: yyyy-MM-dd")
        @ValidDate String endDate) {
}
