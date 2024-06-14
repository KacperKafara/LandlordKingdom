package pl.lodz.p.it.ssbd2024.mol.services.scheduler;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.FixedFee;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.model.LocalState;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.RentRepository;
import pl.lodz.p.it.ssbd2024.mol.services.FixedFeeService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MolSchedulerService {
    private final RentRepository rentRepository;
    private final LocalRepository localRepository;
    private final FixedFeeService fixedFeeService;

    @PreAuthorize("permitAll()")
    public void changeLocalState() {
        List<Rent> rents = rentRepository.findAllByEndDateBeforeAndLocal_State(LocalDate.now(), LocalState.RENTED);

        for (Rent rent : rents) {
            Local local = rent.getLocal();
            local.setState(LocalState.INACTIVE);
            localRepository.saveAndFlush(local);
        }
    }

    @PreAuthorize("permitAll()")
    public void createFixedFee() {
        List<Rent> rents = rentRepository.findAllByEndDateGreaterThanEqual(LocalDate.now());
        LocalDate today = LocalDate.now();

        for (Rent rent : rents) {
            BigDecimal rentalFee = rent.getLocal().getRentalFee();
            BigDecimal marginFee = rent.getLocal().getMarginFee();

            FixedFee fixedFee = new FixedFee(rentalFee, marginFee, today, rent);
            fixedFeeService.createFixedFeeForEndOfBillingPeriod(fixedFee);
        }
    }
}
