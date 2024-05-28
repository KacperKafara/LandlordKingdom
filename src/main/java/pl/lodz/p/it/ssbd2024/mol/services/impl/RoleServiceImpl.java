package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.mol.dto.GetRoleResponse;
import pl.lodz.p.it.ssbd2024.mol.exceptions.RoleRequestAlreadyExistsException;
import pl.lodz.p.it.ssbd2024.mol.exceptions.UserIsOwnerAlreadyException;
import pl.lodz.p.it.ssbd2024.mol.repositories.RoleRequestRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.TenantMolRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.UserMolRepository;
import pl.lodz.p.it.ssbd2024.mol.services.RoleService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {
    private final UserMolRepository userRepository;
    private final TenantMolRepository tenantRepository;
    private final RoleRequestRepository roleRequestRepository;

    @Override
    public GetRoleResponse getAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public GetRoleResponse getRole(UUID tenantId) throws RoleRequestAlreadyExistsException, UserIsOwnerAlreadyException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void accept(UUID id) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void delete(UUID id) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
