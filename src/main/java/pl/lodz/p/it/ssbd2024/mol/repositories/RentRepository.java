package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Rent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RentRepository extends JpaRepository<Rent, UUID>{

    @PreAuthorize("hasRole('TENANT')")
    List<Rent> findAllByTenantIdAndEndDateBefore(UUID tenantId, LocalDateTime endDate);

    @PreAuthorize("hasRole('OWNER')")
    @Query("SELECT r FROM Rent r WHERE r.owner.id = :ownerId AND r.endDate >= CURRENT_DATE")
    List<Rent> findCurrentRentsByLocalId(@Param("ownerId") UUID ownerId);

    @PreAuthorize("hasRole('OWNER')")
    Rent findByOwnerIdAndId(UUID ownerId, UUID rentId);

    @PreAuthorize("hasRole('TENANT')")
    @Query("SELECT r FROM Rent r WHERE r.tenant.id = :tenantId AND r.endDate >= CURRENT_DATE")
    List<Rent> findCurrentRentsByTenantId(@Param("tenantId") UUID tenantId);
}
