package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record Verify2FATokenRequest(
        @NotBlank(message = "Login cannot be blank.")
        @Size(min = 3, max = 50, message = "Login length must be between 3 and 50.")
        String login,

        @NotBlank(message = "Token cannot be blank.")
        @Size(min = 8, max = 8, message = "Token length must be 8.")
        String token
) {
}
