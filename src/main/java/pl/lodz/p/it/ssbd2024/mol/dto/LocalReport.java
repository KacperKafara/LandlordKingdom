package pl.lodz.p.it.ssbd2024.mol.dto;

import pl.lodz.p.it.ssbd2024.model.FixedFee;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.model.VariableFee;

import java.util.List;

public record LocalReport(Local local,
                          List<Payment> payments,
                          List<VariableFee> variableFees,
                          List<FixedFee> fixedFees,
                          int rentCount) {

}
