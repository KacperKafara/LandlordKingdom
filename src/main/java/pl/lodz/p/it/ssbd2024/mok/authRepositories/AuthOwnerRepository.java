package pl.lodz.p.it.ssbd2024.mok.authRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Owner;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
@PreAuthorize("hasRole('ADMINISTRATOR')")
public interface AuthOwnerRepository extends JpaRepository<Owner, UUID> {
    @PreAuthorize("permitAll()")
    Optional<Owner> findByUserIdAndActive(UUID id, boolean active);
}
