package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.RoleRequest;
import pl.lodz.p.it.ssbd2024.exceptions.RoleRequestAlreadyExistsException;
import pl.lodz.p.it.ssbd2024.exceptions.UserAlreadyHasRoleException;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    List<RoleRequest> getAll();

    RoleRequest get() throws NotFoundException;

    RoleRequest requestRole() throws RoleRequestAlreadyExistsException, UserAlreadyHasRoleException, NotFoundException;

    void accept(UUID id) throws NotFoundException;

    void reject(UUID id) throws NotFoundException;
}
