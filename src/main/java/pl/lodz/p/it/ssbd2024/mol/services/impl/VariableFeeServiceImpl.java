package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.VariableFeeAlreadyExistsException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.RentExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.model.VariableFee;
import pl.lodz.p.it.ssbd2024.mol.repositories.RentRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.TenantMolRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.VariableFeeRepository;
import pl.lodz.p.it.ssbd2024.mol.services.VariableFeeService;
import pl.lodz.p.it.ssbd2024.util.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class VariableFeeServiceImpl implements VariableFeeService {
    private final VariableFeeRepository variableFeeRepository;
    private final TenantMolRepository tenantRepository;
    private final RentRepository rentRepository;

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public VariableFee create(UUID userId, UUID rentId, BigDecimal amount)
            throws NotFoundException, VariableFeeAlreadyExistsException {
        Tenant tenant = tenantRepository.findByUserId(userId).get();
        Rent rent = rentRepository.findByIdAndTenantId(rentId, tenant.getId())
                .orElseThrow(() -> new NotFoundException(RentExceptionMessages.RENT_NOT_FOUND, ErrorCodes.NOT_FOUND));

        Optional<VariableFee> existingVariableFee = variableFeeRepository
                .findByRentIdBetween(rentId, userId, DateUtils.getFirstDayOfCurrentWeek(), DateUtils.getLastDayOfCurrentWeek());

        if (existingVariableFee.isPresent()) {
            throw new VariableFeeAlreadyExistsException(
                    RentExceptionMessages.VARIABLE_FEE_ALREADY_EXISTS,
                    ErrorCodes.VARIABLE_FEE_ALREADY_EXISTS);
        }

        VariableFee variableFee = new VariableFee(amount, LocalDate.now(), rent);
        rent.setBalance(rent.getBalance().subtract(amount));
        rentRepository.saveAndFlush(rent);
        return variableFeeRepository.saveAndFlush(variableFee);
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Page<VariableFee> getRentVariableFees(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return variableFeeRepository.findRentVariableFeesBetween(rentId, userId, startDate, endDate, pageable);
    }

}
