package pl.lodz.p.it.ssbd2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssbd2024.model.Administrator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdministratorRepository extends JpaRepository<Administrator, UUID> {
    List<Administrator> findAllByActive(boolean active);

    Optional<Administrator> findByUserId(UUID id);

    Optional<Administrator> findByUserIdAndActive(UUID id, boolean active );

}
