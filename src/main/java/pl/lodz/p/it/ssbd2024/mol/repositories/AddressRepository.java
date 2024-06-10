package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @NonNull
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OWNER')")
    Address saveAndFlush(@NonNull Address address);

    @NonNull
    @PreAuthorize("isAuthenticated()")
    Optional<Address> findById(@NonNull UUID id);

    @NonNull
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OWNER')")
    List<Address> findAll();

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OWNER')")
    @Query("SELECT a FROM Address a WHERE a.country = :country AND a.city = :city AND a.street = :street AND a.number = :number AND a.zip = :zip")
    Optional<Address> findByAddress(String country, String city, String street, String number, String zip);
}
