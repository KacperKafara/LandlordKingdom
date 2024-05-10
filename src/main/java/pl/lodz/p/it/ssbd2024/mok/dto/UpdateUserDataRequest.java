package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDataRequest(
        @NotBlank(message = "First name cannot be blank.")
        @Size(max = 50, message = "First name length must be less than 50.")
        String firstName,

        @NotBlank(message = "Last name cannot be blank.")
        @Size(max = 50, message = "Last name length must be less than 50.")
        String lastName,

        @NotBlank(message = "Language name cannot be blank.")
        @Pattern(regexp = "^(en|pl)$", message = "Language name must be 'en' or 'pl'.")
        String language
) {
}
