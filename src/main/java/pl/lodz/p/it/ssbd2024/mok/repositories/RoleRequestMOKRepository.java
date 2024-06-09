package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.RoleRequest;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RoleRequestMOKRepository extends JpaRepository<RoleRequest, UUID>{

    @Query("SELECT r FROM RoleRequest r WHERE r.tenant.user.id = :userId")
    Optional<RoleRequest> findByUserId(UUID userId);
}
