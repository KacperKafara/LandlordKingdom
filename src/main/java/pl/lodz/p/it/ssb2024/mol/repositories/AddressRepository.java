package pl.lodz.p.it.ssb2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.model.domainmodel.Address;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
