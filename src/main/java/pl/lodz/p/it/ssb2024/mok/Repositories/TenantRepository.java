package pl.lodz.p.it.ssb2024.mok.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.Model.Users.Tenant;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID>{
}
