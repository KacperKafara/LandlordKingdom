package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssbd2024.model.Rent;

import java.util.UUID;

public interface RentRepository extends JpaRepository<Rent, UUID>{
}
