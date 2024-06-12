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
import pl.lodz.p.it.ssbd2024.mol.repositories.FixedFeeRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.RentRepository;

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
    private final FixedFeeRepository fixedFeeRepository;

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
    @Retryable(maxAttempts = 3, retryFor = {OptimisticLockException.class})
    public void createFixedFee() {
        List<Rent> rents = rentRepository.findAllByEndDateGreaterThanEqual(LocalDate.now());
        LocalDate today = LocalDate.now();

        for (Rent rent : rents) {
            long daysPassed = ChronoUnit.DAYS.between(rent.getStartDate(), today);
            BigDecimal rentalFee = rent.getLocal().getRentalFee();
            BigDecimal marginFee = rent.getLocal().getMarginFee();
            Local local = rent.getLocal();

            if (daysPassed == 0) {
                continue;
            }

            if (daysPassed < 7) {
                rentalFee = rentalFee.divide(BigDecimal.valueOf(7), 2, RoundingMode.UP).multiply(BigDecimal.valueOf(daysPassed));
                marginFee = marginFee.divide(BigDecimal.valueOf(7), 2, RoundingMode.UP).multiply(BigDecimal.valueOf(daysPassed));
            }

            FixedFee fixedFee = new FixedFee(rentalFee, marginFee, today, rent);
            fixedFeeRepository.saveAndFlush(fixedFee);
            rent.setBalance(rent.getBalance().subtract(rentalFee).subtract(marginFee));
            rentRepository.saveAndFlush(rent);

            BigDecimal nextMarginFee = local.getNextMarginFee();
            BigDecimal nextRentalFee = local.getNextRentalFee();

            if (nextMarginFee != null) {
                local.setMarginFee(nextMarginFee);
                local.setNextMarginFee(null);
            }

            if (nextRentalFee != null) {
                local.setRentalFee(nextRentalFee);
                local.setNextRentalFee(null);
            }

            localRepository.saveAndFlush(local);
        }
    }
}
