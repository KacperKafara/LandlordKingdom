package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FixedFeeResponse(String date, BigDecimal marginFee, BigDecimal rentalFee) {
}
