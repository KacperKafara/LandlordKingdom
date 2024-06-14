package pl.lodz.p.it.ssbd2024.mol.services.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(propagation = Propagation.NEVER)
public class LocalScheduler {
    private final MolSchedulerService molSchedulerService;

    @Scheduled(fixedRate = 24, timeUnit = TimeUnit.HOURS)
    @PreAuthorize("permitAll()")
    public void changeLocalStateAfterEndedRent() {
        log.info("Changing state of locals with ended rent");
        molSchedulerService.changeLocalState();
    }

    @Scheduled(cron = "0 0 0 * * MON", zone = "UTC")
    @PreAuthorize("permitAll()")
    @Retryable(retryFor = {RuntimeException.class})
    public void createFixedFeeAfterEndOfWeek() {
        log.info("Applying fixed fee for all current rents");
        molSchedulerService.createFixedFee();
    }
}
