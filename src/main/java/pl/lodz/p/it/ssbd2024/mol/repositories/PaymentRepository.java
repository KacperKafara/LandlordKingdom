package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Payment;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @PreAuthorize("hasRole('OWNER')")
    List<Payment> findByRentId(UUID rentId);

    @NonNull
    @PreAuthorize("hasRole('OWNER')")
    Payment saveAndFlush(@NonNull Payment payment);

    @PreAuthorize("hasRole('OWNER')")
    @Query("SELECT payment FROM Payment payment WHERE payment.rent.id = :rentId AND payment.rent.owner.user.id = :userId AND payment.date >= :startDate AND payment.date <= :endDate ORDER BY payment.date DESC")
    Page<Payment> findPaymentsForOwnerBetweenDates(@Param("userId") UUID userId, @Param("rentId") UUID rentId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);
}
