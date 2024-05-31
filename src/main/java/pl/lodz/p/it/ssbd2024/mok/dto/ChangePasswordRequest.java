package pl.lodz.p.it.ssbd2024.mok.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.StringJoiner;

public record ChangePasswordRequest(
        @NotBlank
        @Size(min = 8, max = 50)
        String password,

        @NotBlank(message = "Token cannot be blank.")
        String token
) {
        @Override
        public String toString() {
                return new StringJoiner(", ", ChangePasswordRequest.class.getSimpleName() + "[", "]")
                        .add("password='********'")
                        .add("token='" + token + "'")
                        .toString();
        }
}
