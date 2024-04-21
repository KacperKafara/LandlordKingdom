package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.AccessLevelAlreadyRemovedException;
import pl.lodz.p.it.ssbd2024.model.Owner;

import java.util.UUID;

public interface OwnerService {

    Owner removeOwnerAccessLevel(UUID id) throws AccessLevelAlreadyRemovedException;
}
