package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;

public record OwnLocalDetailsResponse(String name,
                                      int size,
                                      String description,
                                      String state,
                                      AddressResponse address,
                                      BigDecimal marginFee,
                                      BigDecimal rentalFee,
                                      BigDecimal nextMarginFee,
                                      BigDecimal nextRentalFee
) {
}