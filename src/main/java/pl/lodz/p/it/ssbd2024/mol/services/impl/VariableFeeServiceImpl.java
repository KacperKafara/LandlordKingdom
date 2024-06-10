package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.VariableFee;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.VariableFeeRepository;
import pl.lodz.p.it.ssbd2024.mol.services.VariableFeeService;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class VariableFeeServiceImpl implements VariableFeeService {
    private final VariableFeeRepository variableFeeRepository;
    private final LocalRepository localRepository;

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public VariableFee create(VariableFee fee) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Page<VariableFee> getRentVariableFees(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return variableFeeRepository.findRentVariableFeesBetween(rentId, userId, startDate, endDate, pageable);
    }

}
