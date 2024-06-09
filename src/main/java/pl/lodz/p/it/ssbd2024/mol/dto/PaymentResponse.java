package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;

public record PaymentResponse(String date, BigDecimal amount) {
}
