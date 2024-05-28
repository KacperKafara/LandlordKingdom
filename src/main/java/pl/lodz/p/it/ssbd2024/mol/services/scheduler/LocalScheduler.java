package pl.lodz.p.it.ssbd2024.mol.services.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LocalScheduler {
    public void changeLocalStateAfterEndedRent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void createFixedFeeAfterEndOfWeek() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
