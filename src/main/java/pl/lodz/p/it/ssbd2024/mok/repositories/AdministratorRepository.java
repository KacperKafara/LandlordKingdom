package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Administrator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
@PreAuthorize("hasRole('ADMINISTRATOR')")
public interface AdministratorRepository extends JpaRepository<Administrator, UUID>, JpaSpecificationExecutor<Administrator> {
    Optional<Administrator> findByUserId(UUID id);

    @PreAuthorize("permitAll()")
    Optional<Administrator> findByUserIdAndActive(UUID id, boolean active );

}
