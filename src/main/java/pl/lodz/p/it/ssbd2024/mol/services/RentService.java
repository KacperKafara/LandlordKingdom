package pl.lodz.p.it.ssbd2024.mol.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.RentAlreadyEndedException;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.exceptions.WrongEndDateException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentService {
    Rent getTenantRent(UUID rentId, UUID userId) throws NotFoundException;

    Page<Rent> getCurrentOwnerRents(UUID ownerId, Pageable pageable);

    Rent editEndDate(UUID rentId, UUID userId, LocalDate newEndDate) throws WrongEndDateException, NotFoundException, RentAlreadyEndedException;

    List<Rent> getCurrentTenantRents(UUID userId);

    List<Rent> getArchivalRentsForTenant(UUID userId);

    Rent getOwnerRent(UUID userId, UUID rentId) throws NotFoundException;

    Page<Rent> getArchivalOwnerRents(UUID userId, Pageable pageable);
}
