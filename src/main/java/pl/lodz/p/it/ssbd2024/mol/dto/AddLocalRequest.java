package pl.lodz.p.it.ssbd2024.mol.dto;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Owner;
import java.math.BigDecimal;
import java.util.UUID;

public record AddLocalRequest(
        String name,
        String description,
        int size,
        AddressResponse address,
        BigDecimal marginFee,
        BigDecimal rentalFee
) {
}
