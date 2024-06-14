package pl.lodz.p.it.ssbd2024.mol.services.impl;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.PaymentAlreadyExistsException;
import pl.lodz.p.it.ssbd2024.exceptions.RentAlreadyEndedException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.LocalExceptionMessages;
import pl.lodz.p.it.ssbd2024.messages.RentExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.mol.repositories.OwnerMolRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.PaymentRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.RentRepository;
import pl.lodz.p.it.ssbd2024.mol.services.PaymentService;
import pl.lodz.p.it.ssbd2024.util.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OwnerMolRepository ownerMolRepository;
    private final RentRepository rentRepository;

    @Override
    @PreAuthorize("hasAnyRole('OWNER', 'TENANT')")
    public Page<Payment> getRentPayments(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return paymentRepository.findPaymentsForOwnerBetweenDates(userId, rentId, startDate, endDate, pageable);
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Payment create(UUID userId, UUID rentId, BigDecimal amount) throws NotFoundException, PaymentAlreadyExistsException, RentAlreadyEndedException {
        Owner owner = ownerMolRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));
        Rent rent = rentRepository.findByOwnerIdAndId(owner.getId(), rentId)
                .orElseThrow(() -> new NotFoundException(RentExceptionMessages.RENT_NOT_FOUND, ErrorCodes.NOT_FOUND));

        if (rent.getEndDate().isBefore(LocalDate.now())) {
            throw new RentAlreadyEndedException(
                    RentExceptionMessages.RENT_ENDED,
                    ErrorCodes.RENT_ENDED);
        }

        Optional<Payment> existingPayment = paymentRepository
                .findByRentIdBetween(rentId, userId, DateUtils.getFirstDayOfCurrentWeek(), DateUtils.getLastDayOfCurrentWeek());

        if (existingPayment.isPresent()) {
            throw new PaymentAlreadyExistsException(
                    RentExceptionMessages.PAYMENT_ALREADY_EXISTS,
                    ErrorCodes.PAYMENT_ALREADY_EXISTS);
        }

        Payment payment = new Payment(amount, LocalDate.now(), rent);
        rent.setBalance(rent.getBalance().add(amount));
        rentRepository.saveAndFlush(rent);
        return paymentRepository.saveAndFlush(payment);
    }
}
