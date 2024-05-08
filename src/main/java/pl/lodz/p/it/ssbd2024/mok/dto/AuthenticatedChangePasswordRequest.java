package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticatedChangePasswordRequest(
        @NotBlank @Size(min = 8) String oldPassword,
        @NotBlank @Size(min = 8) String newPassword) {
}
