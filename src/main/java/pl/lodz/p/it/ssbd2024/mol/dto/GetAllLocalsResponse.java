package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record GetAllLocalsResponse(
        UUID id,
        String ownerLogin,
        String name,
        String description,
        String state,
        int size,
        BigDecimal marginFee,
        BigDecimal rentFee,
        AddressResponse address
) {
}
