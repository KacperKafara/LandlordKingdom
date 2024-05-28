package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.VariableFee;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.VariableFeeRepository;
import pl.lodz.p.it.ssbd2024.mol.services.VariableFeeService;

@Service
@RequiredArgsConstructor
@Transactional
public class VariableFeeServiceImpl implements VariableFeeService {
    private final VariableFeeRepository variableFeeRepository;
    private final LocalRepository localRepository;

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public VariableFee create(VariableFee fee) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
