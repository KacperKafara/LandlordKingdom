package pl.lodz.p.it.ssb2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.model.Administrator;

import java.util.Optional;
import java.util.UUID;

public interface AdministratorRepository extends JpaRepository<Administrator, UUID> {

    Optional<Administrator> findByUserId(UUID id);

    Optional<Administrator> findByUserIdAndActive(UUID id, boolean active );

}
