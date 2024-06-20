package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.RentAlreadyEndedException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.RentExceptionMessages;
import pl.lodz.p.it.ssbd2024.messages.RentMessages;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.exceptions.WrongEndDateException;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.mol.repositories.RentRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.TenantMolRepository;
import pl.lodz.p.it.ssbd2024.mol.services.RentService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RentServiceImpl implements RentService {
    private final RentRepository rentRepository;
    private final TenantMolRepository tenantRepository;

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public Rent getTenantRent(UUID rentId, UUID userId) throws NotFoundException {
        Tenant tenant = tenantRepository.findByUserId(userId).get();
        return rentRepository.findByIdAndTenantId(rentId, tenant.getId())
                .orElseThrow(() -> new NotFoundException(RentMessages.RENT_NOT_FOUND, ErrorCodes.RENT_NOT_FOUND));
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Page<Rent> getCurrentOwnerRents(UUID userId, Pageable pageable) {
        return rentRepository.findCurrentRentsByOwnerId(userId, pageable);
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Rent editEndDate(UUID rentId, UUID userId, LocalDate newEndDate) throws WrongEndDateException, NotFoundException, RentAlreadyEndedException {
        Rent rent = rentRepository.findByOwner_User_IdAndId(userId, rentId).orElseThrow(() -> new NotFoundException(RentExceptionMessages.RENT_NOT_FOUND, ErrorCodes.RENT_NOT_FOUND));

        if (newEndDate.isBefore(LocalDate.now())
                || !newEndDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || newEndDate.equals(rent.getEndDate())) {
            throw new WrongEndDateException(RentExceptionMessages.WRONG_END_DATE, ErrorCodes.WRONG_END_DATE);
        }
        if (rent.getEndDate().isBefore(LocalDate.now())) {
            throw new RentAlreadyEndedException(RentExceptionMessages.RENT_ENDED, ErrorCodes.RENT_ENDED);
        }
        rent.setEndDate(newEndDate);
        return rentRepository.saveAndFlush(rent);
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

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Rent getOwnerRent(UUID userId, UUID rentId) throws NotFoundException {
        return rentRepository.findByOwner_User_IdAndId(userId, rentId).orElseThrow(() -> new NotFoundException(RentExceptionMessages.RENT_NOT_FOUND, ErrorCodes.RENT_NOT_FOUND));
    }

    @Override
    public List<Rent> getArchivalOwnerRents(UUID userId) {
        return rentRepository.findArchivalRentsByOwnerUserId(userId);
    }

}
