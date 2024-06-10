package pl.lodz.p.it.ssbd2024.mol.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record SetFixedFeeRequest(
        @NotBlank(message = "fixed fee cannot be blank.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Fixed fee must be greater than 0.")
        @DecimalMax(value = "10000", inclusive = true, message = "Fixed fee must be less than or equal to 10 000.00")
        @Digits(integer = 10, fraction = 2, message = "Fixed fee must be a valid monetary amount with up to 10 integer digits and 2 fractional digits.")
        BigDecimal rentalFee,

        @NotBlank(message = "fixed fee cannot be blank.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Fixed fee must be greater than 0.")
        @DecimalMax(value = "10000", inclusive = true, message = "Fixed fee must be less than or equal to 10 000.00")
        @Digits(integer = 10, fraction = 2, message = "Fixed fee must be a valid monetary amount with up to 10 integer digits and 2 fractional digits.")
        BigDecimal marginFee) {
}
