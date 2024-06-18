package pl.lodz.p.it.ssbd2024.mol.dto;

import java.util.List;

public record GetUnapprovedLocalPageResponse(
        int totalPages,
        List<GetAllLocalsResponse> locals) {
}
