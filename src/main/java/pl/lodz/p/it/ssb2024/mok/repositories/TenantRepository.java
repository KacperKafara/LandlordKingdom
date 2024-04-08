package pl.lodz.p.it.ssb2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.model.Tenant;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID>{
}
