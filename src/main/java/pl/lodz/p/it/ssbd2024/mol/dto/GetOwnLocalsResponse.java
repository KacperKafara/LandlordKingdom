package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record GetOwnLocalsResponse(
        UUID id,
        String name,
        String description,
        String state,
        int size,
        BigDecimal marginFee,
        BigDecimal rentFee,
        BigDecimal nextMarginFee,
        BigDecimal nextRentFee,
        AddressResponse address
) {
}
