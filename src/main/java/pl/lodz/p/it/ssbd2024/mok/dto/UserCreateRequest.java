package pl.lodz.p.it.ssbd2024.mok.dto;

import jakarta.validation.constraints.*;

import java.util.StringJoiner;

public record UserCreateRequest(
        @NotBlank(message = "Login cannot be blank.")
        @Size(min = 3, max = 50, message = "Login must be between 3 and 50 characters.")
        String login,

        @Email(message = "Email should be valid.")
        @NotBlank(message = "Email cannot be blank.")
        @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters.")
        String email,

        @NotBlank
        @Size(min = 1, max = 50, message = "First name length must be between 1 and 50.")
        String firstName,

        @NotBlank
        @Size(min = 1, max = 50, message = "Last name length must be between 1 and 50.")
        String lastName,

        @NotBlank @Size(min = 8, max = 50, message = "Password length must be between 8 and 50.")
        String password,

        @NotBlank(message = "Language name cannot be blank.")
        @Pattern(regexp = "^(en|pl)$", message = "Language name must be 'en' or 'pl'.")
        String language) {
        @Override
        public String toString() {
                return new StringJoiner(", ", UserCreateRequest.class.getSimpleName() + "[", "]")
                        .add("login='" + login + "'")
                        .add("email='" + email + "'")
                        .add("firstName='" + firstName + "'")
                        .add("lastName='" + lastName + "'")
                        .add("password='********'")
                        .add("language='" + language + "'")
                        .toString();
        }
}