package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Tenant;

import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TenantMolRepository extends JpaRepository<Tenant, UUID> {
}
