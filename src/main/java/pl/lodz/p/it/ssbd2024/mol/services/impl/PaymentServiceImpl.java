package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Payment;
import pl.lodz.p.it.ssbd2024.mol.repositories.PaymentRepository;
import pl.lodz.p.it.ssbd2024.mol.services.PaymentService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    @PreAuthorize("hasAnyRole('OWNER', 'TENANT')")
    public Page<Payment> getRentPayments(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return paymentRepository.findPaymentsForOwnerBetweenDates(userId, rentId, startDate, endDate, pageable);
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Payment create(Payment payment) throws NotFoundException {
        return null;
    }
}
