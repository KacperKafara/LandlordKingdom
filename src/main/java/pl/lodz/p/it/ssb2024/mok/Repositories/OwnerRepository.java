package pl.lodz.p.it.ssb2024.mok.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.Model.Users.Owner;

import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID>{
}
