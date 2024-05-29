package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.ssbd2024.model.Owner;

import java.util.Optional;
import java.util.UUID;

public interface OwnerMolRepository extends JpaRepository<Owner, UUID> {
    @PreAuthorize("hasAnyRole('TENANT', 'OWNER')")
    Optional<Owner> findByUserId(UUID id);

    @NonNull
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    Owner saveAndFlush(@NonNull Owner owner);
}
