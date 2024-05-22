package pl.lodz.p.it.ssbd2024.mok.authRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.Tenant;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthTenantRepository extends JpaRepository<Tenant, UUID> {
    Optional<Tenant> findByUserIdAndActive(UUID id, boolean active);
}
