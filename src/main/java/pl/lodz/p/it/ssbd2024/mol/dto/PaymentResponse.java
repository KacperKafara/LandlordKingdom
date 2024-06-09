package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;

public record PaymentResponse(
        BigDecimal amount,
        String date
) {
}
