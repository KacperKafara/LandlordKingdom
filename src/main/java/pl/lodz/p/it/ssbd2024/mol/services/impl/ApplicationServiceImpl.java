package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssbd2024.model.Application;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.mol.repositories.ApplicationRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.FixedFeeRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.RentRepository;
import pl.lodz.p.it.ssbd2024.mol.services.ApplicationService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final RentRepository rentRepository;
    private final LocalRepository localRepository;
    private final FixedFeeRepository fixedFeeRepository;

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public List<Application> getLocalApplications(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Rent acceptApplication(UUID applicationId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
