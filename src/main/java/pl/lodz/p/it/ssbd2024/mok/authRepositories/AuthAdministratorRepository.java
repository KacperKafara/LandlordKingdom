package pl.lodz.p.it.ssbd2024.mok.authRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.Administrator;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthAdministratorRepository extends JpaRepository<Administrator, UUID> {
    Optional<Administrator> findByUserIdAndActive(UUID id, boolean active);
}
