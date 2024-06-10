package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record GetActiveLocalsResponse(
        UUID id,
        String name,
        String description,
        int size,
        BigDecimal marginFee,
        BigDecimal rentFee,
        AddressResponse address
) {
}
