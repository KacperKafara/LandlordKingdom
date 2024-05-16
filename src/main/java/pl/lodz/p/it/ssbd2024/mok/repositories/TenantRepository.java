package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.lodz.p.it.ssbd2024.model.Tenant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID>, JpaSpecificationExecutor<Tenant> {
    List<Tenant> findAllByActive(boolean active);

    Optional<Tenant> findByUserIdAndActive(UUID user_id, boolean active);

    Optional<Tenant> findByUserId(UUID id);
}
