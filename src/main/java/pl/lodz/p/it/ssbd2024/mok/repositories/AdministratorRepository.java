package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.Administrator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, UUID>, JpaSpecificationExecutor<Administrator> {
    List<Administrator> findAllByActive(boolean active);

    Optional<Administrator> findByUserId(UUID id);

    Optional<Administrator> findByUserIdAndActive(UUID id, boolean active );

}
