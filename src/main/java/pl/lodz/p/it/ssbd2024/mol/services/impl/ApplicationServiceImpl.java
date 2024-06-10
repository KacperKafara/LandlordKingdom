package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.InvalidLocalState;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Application;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.exceptions.LocalAlreadyRentedException;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.mol.repositories.*;
import pl.lodz.p.it.ssbd2024.mol.services.ApplicationService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final RentRepository rentRepository;
    private final LocalRepository localRepository;
    private final FixedFeeRepository fixedFeeRepository;
    private final TenantMolRepository tenantMolRepository;

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public List<Application> getLocalApplications(UUID localId, UUID ownerId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public List<Application> getUserApplications(UUID id) throws NotFoundException {
        Tenant tenant = tenantMolRepository.findByUserId(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
        return applicationRepository.findByTenantId(tenant.getId());
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public Rent acceptApplication(UUID applicationId) throws NotFoundException, LocalAlreadyRentedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public void rejectApplication(UUID applicationId) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public Application createApplication(UUID localId, UUID userId) throws NotFoundException, InvalidLocalState {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public void removeApplication(UUID localId, UUID userId) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}