package pl.lodz.p.it.ssbd2024.mol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record EditLocalRequest(
        UUID id,
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 1, max = 200, message = "Name length has to be between 1 and 200")
        String name,
        @NotBlank(message = "Description cannot be blank")
        @Size(min = 1, max = 1500, message = "Description length has to be between 1 and 1500")
        String description,
        @Pattern(regexp = "ACTIVE|INACTIVE")
        String state
) {
}
