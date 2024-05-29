package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.VariableFee;

public interface VariableFeeService {

    VariableFee create(VariableFee fee) throws NotFoundException;
}
