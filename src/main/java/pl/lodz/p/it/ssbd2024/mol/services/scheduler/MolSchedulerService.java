package pl.lodz.p.it.ssbd2024.mol.services.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class MolSchedulerService {
    @PreAuthorize("permitAll()")
    public void changeLocalState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PreAuthorize("permitAll()")
    public void createFixedFee() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
