package pl.lodz.p.it.ssbd2024.mol.services;

import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.exceptions.WrongEndDateException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RentService {

    Rent getRent(UUID id) throws NotFoundException;

    Rent getTenantRent(UUID rentId, UUID userId) throws NotFoundException;

    List<Rent> getCurrentOwnerRents(UUID ownerId);

    Rent payRent(UUID rentId, UUID ownerId, Payment payment) throws NotFoundException;

    Rent editEndDate(UUID rentId, UUID userId, LocalDate newEndDate) throws WrongEndDateException, NotFoundException;

    List<Rent> getCurrentTenantRents(UUID userId);

    List<Rent> getArchivalRentsForTenant(UUID userId);

    Rent getOwnerRent(UUID userId, UUID rentId) throws NotFoundException;

}
