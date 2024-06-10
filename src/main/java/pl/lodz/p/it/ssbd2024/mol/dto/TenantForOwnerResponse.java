package pl.lodz.p.it.ssbd2024.mol.dto;

public record TenantForOwnerResponse(
        String login,
        String firstName,
        String lastName,
        String email
) {
}
