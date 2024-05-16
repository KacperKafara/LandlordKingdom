package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(

        @Email(message = "Email must be valid.")
        @NotBlank(message = "Email cannot be blank.")
        @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters.")
        String email

) {
}
