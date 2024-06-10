package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.CreationException;
import pl.lodz.p.it.ssbd2024.exceptions.InvalidLocalState;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Application;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.exceptions.LocalAlreadyRentedException;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    List<Application> getLocalApplications(UUID localId, UUID ownerId);

    List<Application> getUserApplications(UUID id);

    Application getUserApplication(UUID userId, UUID localId) throws NotFoundException;

    Rent acceptApplication(UUID applicationId) throws NotFoundException, LocalAlreadyRentedException;

    void rejectApplication(UUID applicationId) throws NotFoundException;

    Application createApplication(UUID localId, UUID userId) throws NotFoundException, InvalidLocalState, CreationException;

    void removeApplication(UUID localId, UUID userId) throws NotFoundException;
}
