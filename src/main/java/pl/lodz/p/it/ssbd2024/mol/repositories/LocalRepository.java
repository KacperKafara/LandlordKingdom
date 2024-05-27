package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.model.LocalState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface LocalRepository extends JpaRepository<Local, UUID> {
    @PreAuthorize("hasRole('OWNER')")
    List<Local> findByAddress(Address address);

    @PreAuthorize("hasRole('OWNER')")
    List<Local> findAllByOwnerId(UUID id);

    @PreAuthorize("isAuthenticated()")
    List<Local> findAllByState(LocalState localState);

    @PreAuthorize("hasRole('OWNER')")
    Optional<Local> findByOwnerIdAndId(UUID ownerId, UUID id);

}
