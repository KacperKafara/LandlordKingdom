package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssbd2024.model.Owner;

import java.util.Optional;
import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID>{
    Optional<Owner> findByUserIdAndActive(UUID user_id, boolean active);

    Optional<Owner> findByUserId(UUID id);
}
