package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.*;

public record UserCreateRequest(
                @NotBlank String login,
                @NotBlank @Email String email,
                @NotBlank @Size(min = 1, max = 50) String firstName,
                @NotBlank @Size(min = 1, max = 50) String lastName,
                @NotBlank @Size(min = 8) String password) {
}