package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record AuthenticationRequest(
        @NotBlank(message = "Login cannot be blank.")
        @Size(min = 3, max = 50, message = "Login must be between 3 and 50 characters.")
        String login,

        @NotBlank(message = "Password cannot be blank.")
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters.")
        String password,

        @NotBlank(message = "Language name cannot be blank.")
        @Pattern(regexp = "^(en|pl)$", message = "Language name must be 'en' or 'pl'.")
        String language
) {
}
