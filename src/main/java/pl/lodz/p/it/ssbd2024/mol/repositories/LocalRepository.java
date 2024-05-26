package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.model.Local;

import java.util.List;
import java.util.UUID;

@Repository
public interface LocalRepository extends JpaRepository<Local, UUID> {
    @PreAuthorize("hasRole('OWNER')")
    List<Local> findByAddress(Address address);

    @PreAuthorize("hasRole('OWNER')")
    List<Local> findByOwnerId(UUID id);

    @PreAuthorize("hasRole('OWNER')")
    Local findByOwnerIdAndId(UUID ownerId, UUID id);
}
