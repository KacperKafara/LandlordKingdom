package pl.lodz.p.it.ssb2024.mok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.model.users.Administrator;

import java.util.UUID;

public interface AdministratorRepository extends JpaRepository<Administrator, UUID> {
}
