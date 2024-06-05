package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;

public record LocalForTenantResponse(
        String name,
        String description,
        int size,
        AddressResponse address,
        BigDecimal marginFee,
        BigDecimal rentalFee
) {
}
