package pl.lodz.p.it.ssbd2024.mol.dto;

import pl.lodz.p.it.ssbd2024.model.LocalState;

import java.math.BigDecimal;

public record OwnLocalDetailsResponse(String name,
                                      int size,
                                      String description,
                                      LocalState state,
                                      AddressResponse address,
                                      BigDecimal marginFee,
                                      BigDecimal rentalFee,
                                      BigDecimal nextMarginFee,
                                      BigDecimal nextRentalFee
) {
}