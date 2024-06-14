package pl.lodz.p.it.ssbd2024.mol.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NewPaymentRequest(
        @NotNull(message = "Payment cannot be null.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Payment must be greater than 0.")
        @DecimalMax(value = "1000000", inclusive = true, message = "Payment must be less than or equal to 1 000 000.00")
        @Digits(integer = 10, fraction = 2, message = "Payment must be a valid monetary amount with up to 10 integer digits and 2 fractional digits.")
        BigDecimal amount) {
}
