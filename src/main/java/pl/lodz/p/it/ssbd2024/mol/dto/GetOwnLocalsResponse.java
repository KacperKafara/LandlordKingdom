package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;

public record GetOwnLocalsResponse(
        String name,
        String description,
        String state,
        int size,
        BigDecimal marginFee,
        BigDecimal rentFee,
        AddressResponse address
) {
}
