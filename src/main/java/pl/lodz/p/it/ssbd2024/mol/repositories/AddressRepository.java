package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Address;

import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @NonNull
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OWNER')")
    Address saveAndFlush(@NonNull Address address);

    @NonNull
    @PreAuthorize("isAuthenticated()")
    Optional<Address> findById(@NonNull UUID id);
}
