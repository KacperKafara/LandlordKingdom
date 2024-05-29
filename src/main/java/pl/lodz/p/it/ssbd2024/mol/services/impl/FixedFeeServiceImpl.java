package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.FixedFee;
import pl.lodz.p.it.ssbd2024.mol.repositories.FixedFeeRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.services.FixedFeeService;

@Service
@RequiredArgsConstructor
@Transactional
public class FixedFeeServiceImpl implements FixedFeeService {
    private final FixedFeeRepository fixedFeeRepository;
    private final LocalRepository localRepository;

    @Override
    @PreAuthorize("permitAll()")
    public FixedFee create(FixedFee fee) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
