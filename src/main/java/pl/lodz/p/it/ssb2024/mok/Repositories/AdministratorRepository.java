package pl.lodz.p.it.ssb2024.mok.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.Model.Users.Administrator;

import java.util.UUID;

public interface AdministratorRepository extends JpaRepository<Administrator, UUID> {
}
