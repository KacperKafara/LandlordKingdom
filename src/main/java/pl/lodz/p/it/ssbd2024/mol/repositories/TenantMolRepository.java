package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.Tenant;

import java.util.UUID;

@Repository
public interface TenantMolRepository extends JpaRepository<Tenant, UUID> {
}
