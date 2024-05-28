package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.mol.repositories.PaymentRepository;
import pl.lodz.p.it.ssbd2024.mol.services.PaymentService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public List<Payment> getLocalPayments(UUID localId, UUID ownerId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
