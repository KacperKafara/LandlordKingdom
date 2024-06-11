package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
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

    @NonNull
    @PreAuthorize("permitAll()")
    Optional<Local> findById(@NonNull UUID id);

    @PreAuthorize("hasRole('OWNER')")
    List<Local> findByAddress(Address address);

    @PreAuthorize("hasRole('OWNER')")
    @Query("SELECT l FROM Local l WHERE l.address = :address AND l.state != :state")
    Optional<Local> findByAddressAndStateNotContaining(@Param("address") Address address, @Param("state") LocalState state);

    @NonNull
    @PreAuthorize("permitAll()")
    Local saveAndFlush(@NonNull Local local);

    @PreAuthorize("hasRole('OWNER')")
    @Query("SELECT l FROM Local l WHERE l.owner.user.id = :id")
    Page<Local> findAllByOwnerId(UUID id, Pageable pageable);

    @PreAuthorize("hasRole('OWNER')")
    @Query("SELECT l FROM Local l WHERE l.owner.user.id = :id AND l.state = :state")
    Page<Local> findAllByOwnerIdAndState(UUID id, Pageable pageable, LocalState state);

    @PreAuthorize("isAuthenticated()")
    List<Local> findAllByState(LocalState localState);

    @PreAuthorize("hasRole('OWNER')")
    Optional<Local> findByOwner_User_IdAndId(UUID userId, UUID id);

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @NonNull
    Page<Local> findAll(@NonNull Pageable pageable);

    @PreAuthorize("hasRole('TENANT')")
    Optional<Local> findByIdAndState(UUID id, LocalState state);
}
