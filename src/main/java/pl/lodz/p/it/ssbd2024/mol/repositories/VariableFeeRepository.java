package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssbd2024.model.VariableFee;

import java.util.UUID;

public interface VariableFeeRepository extends JpaRepository<VariableFee, UUID>{
}
