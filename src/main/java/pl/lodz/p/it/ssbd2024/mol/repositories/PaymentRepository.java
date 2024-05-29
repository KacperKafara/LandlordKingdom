package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Payment;

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
}
