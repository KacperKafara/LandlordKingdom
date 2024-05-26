package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.ssbd2024.model.User;

import java.util.UUID;

@Repository
public interface UserMolRepository extends JpaRepository<User, UUID> {
}
