package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.Email;

public record ResetPasswordRequest(

        @Email
        String email

) {
}
