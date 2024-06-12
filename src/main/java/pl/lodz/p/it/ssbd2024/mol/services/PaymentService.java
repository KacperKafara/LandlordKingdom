package pl.lodz.p.it.ssbd2024.mol.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.PaymentAlreadyExistsException;
import pl.lodz.p.it.ssbd2024.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PaymentService {

    Page<Payment> getRentPayments(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    Payment create(UUID userId, UUID rentID, BigDecimal amount) throws NotFoundException, PaymentAlreadyExistsException;
}
