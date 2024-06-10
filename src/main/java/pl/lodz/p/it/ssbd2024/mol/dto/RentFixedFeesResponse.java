package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;
import java.util.List;

public record RentFixedFeesResponse(
        List<FixedFeeResponse> rentFixedFees,
        long totalPages
) {
}
