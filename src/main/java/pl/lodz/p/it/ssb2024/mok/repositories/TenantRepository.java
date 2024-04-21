package pl.lodz.p.it.ssb2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.model.Administrator;
import pl.lodz.p.it.ssb2024.model.Tenant;

import java.util.Optional;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID>{
    Optional<Tenant> findByUserIdAndActive(UUID user_id, boolean active);
}
