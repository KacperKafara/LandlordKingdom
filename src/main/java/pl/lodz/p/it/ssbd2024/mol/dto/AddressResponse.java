package pl.lodz.p.it.ssbd2024.mol.dto;

public record AddressResponse(
        String country,
        String city,
        String street,
        String number,
        String zipCode
) {
}
