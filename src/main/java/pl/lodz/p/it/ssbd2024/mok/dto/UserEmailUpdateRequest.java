package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.Email;

public record UserEmailUpdateRequest(
        String token,

        @Email
        String email
) {
}
