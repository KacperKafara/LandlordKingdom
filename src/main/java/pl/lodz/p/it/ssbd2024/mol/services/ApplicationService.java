package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Application;
import pl.lodz.p.it.ssbd2024.model.Rent;
import pl.lodz.p.it.ssbd2024.mol.exceptions.LocalAlreadyRentedException;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    List<Application> getLocalApplications(UUID id);

    List<Application> getUserApplication(UUID id);

    Rent acceptApplication(UUID applicationId) throws NotFoundException, LocalAlreadyRentedException;

    void rejectApplication(UUID applicationId) throws NotFoundException;

    void createApplication(Application application) throws NotFoundException;

    void removeApplication(Application application) throws NotFoundException;
}
