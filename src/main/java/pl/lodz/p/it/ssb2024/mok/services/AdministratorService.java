package pl.lodz.p.it.ssb2024.mok.services;

import pl.lodz.p.it.ssb2024.exceptions.AccessLevelAlreadyRemovedException;
import pl.lodz.p.it.ssb2024.model.Administrator;

import java.util.UUID;

public interface AdministratorService {

    Administrator removeAdministratorAccessLevel(UUID id) throws AccessLevelAlreadyRemovedException;
}
