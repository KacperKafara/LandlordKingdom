package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.FixedFee;

public interface FixedFeeService {

    FixedFee create(FixedFee fee) throws NotFoundException;
}
