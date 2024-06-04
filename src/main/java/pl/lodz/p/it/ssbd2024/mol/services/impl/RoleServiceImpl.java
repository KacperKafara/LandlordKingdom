package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.RoleRequestMessages;
import pl.lodz.p.it.ssbd2024.model.RoleRequest;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.exceptions.RoleRequestAlreadyExistsException;
import pl.lodz.p.it.ssbd2024.exceptions.UserAlreadyHasRoleException;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mol.repositories.RoleRequestRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.TenantMolRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.UserMolRepository;
import pl.lodz.p.it.ssbd2024.mol.services.RoleService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RoleServiceImpl implements RoleService {
    private final UserMolRepository userRepository;
    private final TenantMolRepository tenantRepository;
    private final RoleRequestRepository roleRequestRepository;

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public RoleRequest getAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public RoleRequest get() {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            Tenant tenant = tenantRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(RoleRequestMessages.ROLE_REQUEST_NOT_FOUND, ErrorCodes.NOT_FOUND));
            return roleRequestRepository.findByTenantId(tenant.getId()).orElseThrow(() -> new NotFoundException(RoleRequestMessages.ROLE_REQUEST_NOT_FOUND, ErrorCodes.NOT_FOUND));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public RoleRequest requestRole(UUID tenantId) throws RoleRequestAlreadyExistsException, UserAlreadyHasRoleException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void accept(UUID id) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void reject(UUID id) throws NotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
