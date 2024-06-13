package pl.lodz.p.it.ssbd2024.mol.dto;

import pl.lodz.p.it.ssbd2024.model.FixedFee;
import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.model.VariableFee;

import java.util.List;

public record AllLocalsReport(List<Rent> rents,
                              List<VariableFee> variableFees,
                              List<FixedFee> fixedFees,
                              List<Payment> payments) {
}
