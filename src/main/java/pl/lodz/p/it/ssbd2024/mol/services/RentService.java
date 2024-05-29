package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.exceptions.WrongEndDateException;

import java.util.List;
import java.util.UUID;

public interface RentService {

    Rent getRent(UUID id) throws NotFoundException;

    List<Rent> getCurrentRents(UUID ownerId);

    Rent payForRent(UUID rentId, UUID ownerId, Payment payment) throws NotFoundException;

    Rent editEndDate(UUID rentId, UUID ownerId, Rent rent) throws WrongEndDateException, NotFoundException;

    List<Rent> getCurrentRentsForTenant(UUID tenantId);

    List<Rent> getArchivalRentsForTenant(UUID tenantId);
}
