package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RentDetailedTenantResponse(
        UUID id,
        LocalForTenantResponse local,
        OwnerForTenantResponse owner,
        String startDate,
        String endDate,
        BigDecimal balance,
        List<VariableFeeResponse> variableFees,
        List<FixedFeeResponse> fixedFees,
        List<PaymentResponse> payments
) {
}
