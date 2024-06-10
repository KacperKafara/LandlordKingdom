package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.VariableFee;

import java.math.BigDecimal;
import java.util.UUID;

public interface VariableFeeService {

    VariableFee create(UUID userId, UUID rentId, BigDecimal amount) throws NotFoundException;
}
