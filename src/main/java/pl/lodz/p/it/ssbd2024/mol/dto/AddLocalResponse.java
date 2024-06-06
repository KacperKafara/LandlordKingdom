package pl.lodz.p.it.ssbd2024.mol.dto;

import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.model.LocalState;
import pl.lodz.p.it.ssbd2024.model.Owner;

import java.math.BigDecimal;
import java.util.UUID;

public record AddLocalResponse(
        UUID id,
        String name,
        String description,
        String state,
        int size,
        BigDecimal marginFee,
        BigDecimal rentalFee,
        AddressResponse address
) {
}
