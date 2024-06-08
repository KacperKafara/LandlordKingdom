package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;

public record LocalDetailsForAdminResponse(
        String name,
        int size,
        String description,
        String state,
        OwnerForAdminResponse owner,
        AddressResponse address,
        BigDecimal marginFee,
        BigDecimal rentalFee
) {

}
