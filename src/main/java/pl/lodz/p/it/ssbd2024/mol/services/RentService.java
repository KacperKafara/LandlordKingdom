package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.exceptions.WrongEndDateException;

import java.util.List;
import java.util.UUID;

public interface RentService {

    Rent getRent(UUID id);

    List<Rent> getCurrentRents(UUID ownerId);

    Rent payForRent(UUID rentId, UUID ownerId, Payment payment);

    Rent editEndDate(UUID rentId, UUID ownerId, Rent rent) throws WrongEndDateException;

    List<Rent> getCurrentRentsForTenant(UUID tenantId);

    List<Rent> getArchivalRentsForTenant(UUID tenantId);
}
