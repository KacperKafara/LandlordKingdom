package pl.lodz.p.it.ssbd2024.mol.dto;

import java.util.List;

public record GetAllLocalsFiltered(
        List<GetAllLocalsResponse> locals,
        long totalPages
) {
}
