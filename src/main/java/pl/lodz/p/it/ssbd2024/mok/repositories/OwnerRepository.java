package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Owner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
@PreAuthorize("hasRole('ADMINISTRATOR')")
public interface OwnerRepository extends JpaRepository<Owner, UUID>, JpaSpecificationExecutor<Owner> {
    @NonNull
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    List<Owner> findAll();

    @NonNull
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    Page<Owner> findAll(@NonNull Specification specification, @NonNull Pageable pageable);

    @PreAuthorize("permitAll()")
    Optional<Owner> findByUserIdAndActive(UUID user_id, boolean active);

    @PreAuthorize("permitAll()")
    Optional<Owner> findByUserId(UUID id);
}
