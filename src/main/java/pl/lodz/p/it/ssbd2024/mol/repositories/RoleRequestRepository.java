package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.RoleRequest;
import pl.lodz.p.it.ssbd2024.model.Tenant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RoleRequestRepository extends JpaRepository<RoleRequest, UUID> {

    @NonNull
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    List<RoleRequest> findAll();

    @PreAuthorize("hasRole('TENANT')")
    Optional<RoleRequest> findByTenantId(UUID tenantId);

    @NonNull
    @PreAuthorize("hasAnyRole('TENANT', 'ADMINISTRATOR')")
    void delete(@NonNull RoleRequest roleRequest);

    @NonNull
    @PreAuthorize("hasRole('TENANT')")
    RoleRequest saveAndFlush(@NonNull RoleRequest roleRequest);
}
