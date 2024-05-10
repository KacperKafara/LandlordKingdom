package pl.lodz.p.it.ssbd2024.mok.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(
        @NotBlank
        @Size(min = 8)
        String password,

        String token
) {
}
