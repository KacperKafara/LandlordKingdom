package pl.lodz.p.it.ssb2024.mol.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.ssb2024.Model.DomainModel.Address;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
