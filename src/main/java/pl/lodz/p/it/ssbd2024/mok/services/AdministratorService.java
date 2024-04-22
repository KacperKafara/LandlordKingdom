package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.AccessLevelAlreadyRemovedException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Administrator;

import java.util.UUID;

public interface AdministratorService {

    Administrator addAdministratorAccessLevel(UUID id);
    Administrator removeAdministratorAccessLevel(UUID id) throws AccessLevelAlreadyRemovedException, NotFoundException;
}
