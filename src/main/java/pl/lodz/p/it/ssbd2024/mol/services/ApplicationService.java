package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.model.Application;
import pl.lodz.p.it.ssbd2024.model.Rent;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    List<Application> getLocalApplications(UUID id);
    Rent acceptApplication(UUID applicationId);
}
