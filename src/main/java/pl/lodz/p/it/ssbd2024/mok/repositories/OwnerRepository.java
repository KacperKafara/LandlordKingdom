package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.Owner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, UUID>, JpaSpecificationExecutor<Owner> {
    List<Owner> findAllByActive(boolean active);

    Optional<Owner> findByUserIdAndActive(UUID user_id, boolean active);

    Optional<Owner> findByUserId(UUID id);
}
