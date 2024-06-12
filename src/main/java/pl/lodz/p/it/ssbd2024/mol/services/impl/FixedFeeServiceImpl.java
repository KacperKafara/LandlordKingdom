package pl.lodz.p.it.ssbd2024.mol.services.impl;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.FixedFee;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.mol.repositories.FixedFeeRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.RentRepository;
import pl.lodz.p.it.ssbd2024.mol.services.FixedFeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class FixedFeeServiceImpl implements FixedFeeService {
    private final FixedFeeRepository fixedFeeRepository;
    private final RentRepository rentRepository;
    private final LocalRepository localRepository;

    @Override
    @PreAuthorize("permitAll()")
    @Retryable(maxAttempts = 3, retryFor = {OptimisticLockException.class})
    public FixedFee createFixedFeeForEndOfBillingPeriod(FixedFee fixedFee) {
        Rent rent = fixedFee.getRent();
        Local local = rent.getLocal();
        rent.setBalance(rent.getBalance().subtract(fixedFee.getRentalFee()).subtract(fixedFee.getMarginFee()));

        fixedFeeRepository.saveAndFlush(fixedFee);
        rentRepository.saveAndFlush(rent);

        BigDecimal nextMarginFee = local.getNextMarginFee();
        BigDecimal nextRentalFee = local.getNextRentalFee();

        if (nextMarginFee != null) {
            local.setMarginFee(nextMarginFee);
            local.setNextMarginFee(null);
        }

        if (nextRentalFee != null) {
            local.setRentalFee(nextRentalFee);
            local.setNextRentalFee(null);
        }

        localRepository.saveAndFlush(local);
        return fixedFee;
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER', 'TENANT')")
    public Page<FixedFee> getRentFixedFees(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return fixedFeeRepository.findRentVariableFeesBetween(rentId, userId, startDate, endDate, pageable);
    }
}
