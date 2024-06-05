package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;

public record RentForTenantResponse(
        LocalForTenantResponse local,
        OwnerForTenantResponse owner,
        String startDate,
        String endDate,
        BigDecimal balance
) {
}
