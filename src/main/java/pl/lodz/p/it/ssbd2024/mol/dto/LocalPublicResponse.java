package pl.lodz.p.it.ssbd2024.mol.dto;

import java.math.BigDecimal;

public record LocalPublicResponse(String name,
                                  String description,
                                  int size,
                                  String ownerName,
                                  String city,
                                  BigDecimal marginFee,
                                  BigDecimal rentalFee) {
}
