package pl.lodz.p.it.ssbd2024.mol.services.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocalScheduler {

    @PreAuthorize("permitAll()")
    public void changeLocalStateAfterEndedRent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PreAuthorize("permitAll()")
    public void createFixedFeeAfterEndOfWeek() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
