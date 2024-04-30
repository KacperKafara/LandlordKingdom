package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.*;

public record UserCreateRequest(
        @NotBlank String login,
        @Email String email,
        @NotNull @Size(min = 1) String firstName,
        @NotNull @Size(min = 1) String lastName,
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$") String password) {
}
