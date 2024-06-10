package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.CreationException;
import pl.lodz.p.it.ssbd2024.exceptions.InvalidLocalState;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.ApplicationExceptionMessages;
import pl.lodz.p.it.ssbd2024.messages.LocalExceptionMessages;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.exceptions.LocalAlreadyRentedException;
import pl.lodz.p.it.ssbd2024.mol.repositories.TenantMolRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.ApplicationRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.FixedFeeRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.LocalRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.RentRepository;
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
    private final TenantMolRepository tenantRepository;

    @Override
    @PreAuthorize("hasRole('OWNER')")
    public List<Application> getLocalApplications(UUID localId, UUID ownerId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public List<Application> getUserApplications(UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public Application getUserApplication(UUID userId, UUID localId) throws NotFoundException {
        return applicationRepository.findByTenantUserIdAndLocalId(userId, localId).orElseThrow(() -> new NotFoundException(ApplicationExceptionMessages.NOT_FOUND, ErrorCodes.NOT_FOUND));
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
    public Application createApplication(UUID localId, UUID userId) throws NotFoundException, InvalidLocalState, CreationException {
        if (applicationRepository.findByTenantUserIdAndLocalId(userId, localId).isPresent()) {
            throw new CreationException(ApplicationExceptionMessages.EXISTS, ErrorCodes.APPLICATION_EXISTS);
        }

        Tenant tenant = tenantRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
        Local local = localRepository.findById(localId).orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));

        if (local.getState() != LocalState.ACTIVE) {
            throw new InvalidLocalState(LocalExceptionMessages.LOCAL_NOT_ACTIVE, ErrorCodes.LOCAL_NOT_ACTIVE, LocalState.ACTIVE, local.getState());
        }

        Application application = new Application(tenant, local);
        return applicationRepository.saveAndFlush(application);
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public void removeApplication(UUID localId, UUID userId) throws NotFoundException {
        Application application = applicationRepository.findByTenantUserIdAndLocalId(userId, localId).orElseThrow(() -> new NotFoundException(ApplicationExceptionMessages.NOT_FOUND, ErrorCodes.NOT_FOUND));

        applicationRepository.delete(application);
    }


}