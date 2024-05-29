package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    List<Payment> getRentPayments(UUID rentId);

    Payment create(Payment payment) throws NotFoundException;
}
