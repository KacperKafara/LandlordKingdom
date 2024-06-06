package pl.lodz.p.it.ssbd2024.mol.services.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.model.LocalState;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.RentRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MolSchedulerService {
    private final RentRepository rentRepository;
    private final LocalRepository localRepository;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
