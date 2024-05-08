package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Owner;

import java.util.UUID;

public interface OwnerService {

    Owner addOwnerAccessLevel(UUID id) throws NotFoundException;
    Owner removeOwnerAccessLevel(UUID id) throws NotFoundException;
}
