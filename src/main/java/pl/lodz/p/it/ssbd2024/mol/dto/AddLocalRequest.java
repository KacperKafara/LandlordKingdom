package pl.lodz.p.it.ssbd2024.mol.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record AddLocalRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 1, max = 200, message = "Name length has to be between 1 and 200")
        String name,
        @NotBlank(message = "Description cannot be blank")
        @Size(min = 1, max = 1500, message = "Description length has to be between 1 and 1500")
        String description,
        @Min(value = 1, message = "Size has to be greater than 0")
        int size,
        EditLocalAddressRequest address,
        @DecimalMin(value = "0", message = "Margin fee has to be greater or equal to 0")
        BigDecimal marginFee,
        @DecimalMin(value = "0", message = "Rental fee has to be greater or equal to 0")
        BigDecimal rentalFee
) {
}
