package pl.lodz.p.it.ssb2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssb2024.model.Local;

import java.util.UUID;

@Repository
public interface LocalRepository extends JpaRepository<Local, UUID> {
}
