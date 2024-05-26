package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.model.Rent;

import java.util.List;
import java.util.UUID;

public interface RentService {
    List<Rent> getCurrentRents(UUID ownerId);
    Rent payForRent(UUID rentId, Payment payment);
}
