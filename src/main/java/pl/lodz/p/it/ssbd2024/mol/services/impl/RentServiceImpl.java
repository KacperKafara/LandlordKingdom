package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.exceptions.WrongEndDateException;
import pl.lodz.p.it.ssbd2024.mol.repositories.PaymentRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.RentRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.VariableFeeRepository;
import pl.lodz.p.it.ssbd2024.mol.services.RentService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RentServiceImpl implements RentService {
    private final RentRepository rentRepository;
    private final PaymentRepository paymentRepository;
    private final VariableFeeRepository variableFeeRepository;

    @Override
    @PreAuthorize("hasAnyRole('TENANT', 'OWNER')")
    public Rent getRent(UUID id) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public List<Rent> getCurrentOwnerRents(UUID userId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Rent payRent(UUID rentId, UUID ownerId, Payment payment) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Rent editEndDate(UUID rentId, UUID ownerId, Rent rent) throws WrongEndDateException, NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public List<Rent> getCurrentTenantRents(UUID userId) {
        return rentRepository.findAllCurrentRentsByTenantUserId(userId);
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public List<Rent> getArchivalRentsForTenant(UUID userId) {
        return rentRepository.findAllPastRentsByTenantUserId(userId);
    }

}
