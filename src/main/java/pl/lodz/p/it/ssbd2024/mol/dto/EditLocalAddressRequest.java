package pl.lodz.p.it.ssbd2024.mol.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EditLocalAddressRequest(
        @Size(min = 1, max = 100, message = "Country length must be between 1 and 100.")
        @NotBlank(message = "City cannot be blank.")
        String country,
        @Size(min = 1, max = 100, message = "City length must be between 1 and 100.")
        @NotBlank(message = "City cannot be blank.")
        String city,
        @Size(min = 1, max = 100, message = "Street length must be between 1 and 100.")
        @NotBlank(message = "Street cannot be blank.")
        String street,
        @Size(min = 1, max = 10, message = "Number length must be between 1 and 10.")
        @NotBlank(message = "Number cannot be blank.")
        String number,
        @Pattern(regexp = "\\d{2}-\\d{3}", message = "Zip code must be in format XX-XXX.")
        String zipCode
) {
}
