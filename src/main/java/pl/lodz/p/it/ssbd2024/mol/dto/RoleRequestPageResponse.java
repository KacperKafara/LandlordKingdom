package pl.lodz.p.it.ssbd2024.mol.dto;

import java.util.List;

public record RoleRequestPageResponse(
        int totalPages,
        List<RoleRequestResponse> requests
) {
}
