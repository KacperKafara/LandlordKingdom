package pl.lodz.p.it.ssbd2024.mok.authRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.Owner;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthOwnerRepository extends JpaRepository<Owner, UUID> {
    Optional<Owner> findByUserIdAndActive(UUID id, boolean active);
}
