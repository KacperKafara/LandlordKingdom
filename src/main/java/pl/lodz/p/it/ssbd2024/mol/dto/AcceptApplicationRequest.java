package pl.lodz.p.it.ssbd2024.mol.dto;

import pl.lodz.p.it.ssbd2024.util.ValidDate;

public record AcceptApplicationRequest(
       @ValidDate String endDate) {
}
