package pl.lodz.p.it.ssbd2024.mol.dto;

import pl.lodz.p.it.ssbd2024.util.ValidDate;

import java.time.LocalDate;

public record AcceptApplicationRequest(
       @ValidDate LocalDate endDate) {
}
