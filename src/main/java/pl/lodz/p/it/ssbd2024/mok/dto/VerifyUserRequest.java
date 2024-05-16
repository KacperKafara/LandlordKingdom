package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyUserRequest(
        @NotBlank(message = "Token cannot be blank.")
        String token) {
}
