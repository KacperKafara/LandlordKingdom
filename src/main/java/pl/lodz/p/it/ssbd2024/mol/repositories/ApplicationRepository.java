package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    @NonNull
    @PreAuthorize("hasAnyRole('TENANT' , 'OWNER')")
    Optional<Application> findById(@NonNull UUID id);

    @PreAuthorize("hasRole('TENANT')")
    Optional<Application> findByTenantUserIdAndLocalId(UUID userId, UUID localId);

    @NonNull
    @PreAuthorize("hasRole('TENANT')")
    Application saveAndFlush(@NonNull Application application);

    @PreAuthorize("hasRole('TENANT')")
    List<Application> findByTenantId(UUID id);

    @PreAuthorize("hasRole('OWNER')")
    List<Application> findByLocalId(UUID id);

    @PreAuthorize("hasRole('OWNER')")
    @Query("SELECT a FROM Application a JOIN a.local l WHERE l.id = :localId AND l.owner.user.id = :ownerId")
    List<Application> findByLocalIdAndLocal_OwnerId(@Param("localId") UUID localId, @Param("ownerId") UUID ownerId);

    @PreAuthorize("hasAnyRole('OWNER')")
    void deleteByTenantIdAndLocalIdAndLocal_OwnerId(UUID tenantId, UUID localId, UUID ownerId);

    @PreAuthorize("hasAnyRole('TENANT' , 'OWNER')")
    void delete(@NonNull Application application);
}
