package pl.lodz.p.it.ssbd2024.mol.dto;

public record OwnerForTenantResponse(
        String firstName,
        String lastName,
        String login,
        String email
) {

}
