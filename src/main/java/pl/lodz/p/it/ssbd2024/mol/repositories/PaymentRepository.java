package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.Payment;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @PreAuthorize("hasRole('OWNER')")
    @Query("SELECT p FROM Payment p WHERE p.rent.local.id = :localId")
    List<Payment> findByLocalId(@Param("localId") UUID localId);
}
