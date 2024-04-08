package pl.lodz.p.it.ssb2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.model.Application;

import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
}
