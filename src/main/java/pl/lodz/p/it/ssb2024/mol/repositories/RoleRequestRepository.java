package pl.lodz.p.it.ssb2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.model.domainmodel.RoleRequest;

import java.util.UUID;

public interface RoleRequestRepository extends JpaRepository<RoleRequest, UUID> {
}
