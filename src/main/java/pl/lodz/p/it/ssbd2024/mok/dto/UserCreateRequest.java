package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.*;

public record UserCreateRequest(
        @NotBlank String login,
        @Email String email,
        @NotNull @Size(min = 1) String firstName,
        @NotNull @Size(min = 1) String lastName,
        @NotBlank @Size(min = 8) String password) {
}