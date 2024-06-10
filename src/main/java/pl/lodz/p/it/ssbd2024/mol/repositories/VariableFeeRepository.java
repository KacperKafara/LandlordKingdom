package pl.lodz.p.it.ssbd2024.mol.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.VariableFee;

import java.time.LocalDate;
import java.util.UUID;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface VariableFeeRepository extends JpaRepository<VariableFee, UUID>{

    @NonNull
    @PreAuthorize("hasRole('TENANT')")
    VariableFee saveAndFlush(@NonNull VariableFee variableFee);

    @PreAuthorize("hasRole('OWNER')")
    @Query("SELECT fee FROM VariableFee fee WHERE fee.rent.id = :rentId AND fee.rent.owner.user.id = :userId AND fee.date BETWEEN :startDate AND :endDate")
    Page<VariableFee> findRentVariableFeesBetween(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
