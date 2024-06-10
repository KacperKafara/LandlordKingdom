package pl.lodz.p.it.ssbd2024.mol.dto;

import java.util.UUID;

public record ShortRentResponse(
        UUID id,
        String name,
        String startDate,
        String endDate
) {

}
