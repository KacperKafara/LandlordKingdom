package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.FixedFee;
import pl.lodz.p.it.ssbd2024.mol.repositories.FixedFeeRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.services.FixedFeeService;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class FixedFeeServiceImpl implements FixedFeeService {
    private final FixedFeeRepository fixedFeeRepository;
    private final LocalRepository localRepository;

    @Override
    @PreAuthorize("permitAll()")
    public FixedFee create(FixedFee fee) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Page<FixedFee> getRentFixedFees(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return fixedFeeRepository.findRentVariableFeesBetween(rentId, userId, startDate, endDate, pageable);
    }
}
