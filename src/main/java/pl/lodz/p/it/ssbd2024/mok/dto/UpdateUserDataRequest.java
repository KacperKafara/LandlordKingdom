package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UpdateUserDataRequest(
        @NotNull(message = "First name cannot be null.")
        @NotBlank(message = "First name cannot be blank.")
        @Length(max = 50, message = "First name length must be less than 50.")
        String firstName,
        @NotNull(message = "Last name cannot be null.")
        @NotBlank(message = "Last name cannot be blank.")
        @Length(max = 50, message = "Last name length must be less than 50.")
        String lastName,
        @NotNull(message = "Language name cannot be null.")
        @NotBlank(message = "Language name cannot be blank.")
        @Pattern(regexp = "^(en-US|pl)$", message = "Language name must be 'en-US' or 'pl'.")
        String language
) {
}
