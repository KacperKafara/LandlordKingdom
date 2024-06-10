package pl.lodz.p.it.ssbd2024.mol.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.FixedFee;

import java.time.LocalDate;
import java.util.UUID;

public interface FixedFeeService {

    FixedFee create(FixedFee fee) throws NotFoundException;

    Page<FixedFee> getRentFixedFees(UUID rentId, UUID userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
