package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserEmailUpdateRequest(
        @NotBlank(message = "Token cannot be blank.")
        String token,

        @NotBlank
        @Size(min = 8, max = 50)
        String password
) {
}
