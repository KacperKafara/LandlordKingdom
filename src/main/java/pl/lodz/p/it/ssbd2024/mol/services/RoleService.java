package pl.lodz.p.it.ssbd2024.mol.services;

import pl.lodz.p.it.ssbd2024.mol.dto.GetRoleResponse;
import pl.lodz.p.it.ssbd2024.mol.exceptions.RoleRequestAlreadyExistsException;
import pl.lodz.p.it.ssbd2024.mol.exceptions.UserIsOwnerAlreadyException;

import java.util.UUID;

public interface RoleService {
    GetRoleResponse getRole(UUID tenantId) throws RoleRequestAlreadyExistsException, UserIsOwnerAlreadyException;
}
