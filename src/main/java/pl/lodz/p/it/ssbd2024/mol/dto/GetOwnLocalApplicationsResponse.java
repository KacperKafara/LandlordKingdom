package pl.lodz.p.it.ssbd2024.mol.dto;

import java.util.UUID;

public record GetOwnLocalApplicationsResponse(
        UUID id,
        String createdAt,
        String applicantFirstName,
        String applicantLastName,
        String applicantEmail
) {
}
