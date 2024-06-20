package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import pl.lodz.p.it.ssbd2024.model.Owner;
import pl.lodz.p.it.ssbd2024.model.RoleRequest;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.exceptions.RoleRequestAlreadyExistsException;
import pl.lodz.p.it.ssbd2024.exceptions.UserAlreadyHasRoleException;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mol.repositories.OwnerMolRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.RoleRequestRepository;
import pl.lodz.p.it.ssbd2024.mol.repositories.TenantMolRepository;
import pl.lodz.p.it.ssbd2024.mol.services.MolEmailService;
import pl.lodz.p.it.ssbd2024.mol.services.RoleService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RoleServiceImpl implements RoleService {
    private final TenantMolRepository tenantRepository;
    private final RoleRequestRepository roleRequestRepository;
    private final OwnerMolRepository ownerRepository;
    private final MolEmailService molEmailService;

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Page<RoleRequest> getAll(Pageable pageable) {
        return roleRequestRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public RoleRequest get() {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            Tenant tenant = tenantRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(RoleRequestMessages.ROLE_REQUEST_NOT_FOUND, ErrorCodes.ROLE_REQUEST_NOT_FOUND));
            return roleRequestRepository.findByTenantId(tenant.getId()).orElseThrow(() -> new NotFoundException(RoleRequestMessages.ROLE_REQUEST_NOT_FOUND, ErrorCodes.ROLE_REQUEST_NOT_FOUND));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Override
    @PreAuthorize("hasRole('TENANT')")
    public RoleRequest requestRole() throws RoleRequestAlreadyExistsException, UserAlreadyHasRoleException, NotFoundException {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        Tenant tenant = tenantRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(RoleRequestMessages.ROLE_REQUEST_NOT_FOUND, ErrorCodes.ROLE_REQUEST_NOT_FOUND));

        if (roleRequestRepository.findByTenantId(tenant.getId()).isPresent()) {
            throw new RoleRequestAlreadyExistsException(RoleRequestMessages.ROLE_REQUEST_ALREADY_EXISTS, ErrorCodes.ROLE_REQUEST_ALREADY_EXISTS);
        }

        if (ownerRepository.findByUserIdAndActiveIsTrue(userId).isPresent()) {
            throw new UserAlreadyHasRoleException(RoleRequestMessages.USER_ALREADY_HAS_ROLE, ErrorCodes.USER_ALREADY_HAS_ROLE);
        }

        RoleRequest roleRequest = new RoleRequest(tenant);
        return roleRequestRepository.saveAndFlush(roleRequest);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void accept(UUID id) throws NotFoundException {
        RoleRequest roleRequest = roleRequestRepository.findById(id).orElseThrow(() -> new NotFoundException(RoleRequestMessages.ROLE_REQUEST_NOT_FOUND, ErrorCodes.ROLE_REQUEST_NOT_FOUND));
        User user = roleRequest.getTenant().getUser();
        Owner owner = new Owner();
        owner.setUser(user);
        owner.setActive(true);
        ownerRepository.saveAndFlush(owner);
        roleRequestRepository.delete(roleRequest);
        molEmailService.sendRoleRequestAcceptedEmail(user.getEmail(), user.getFirstName(), user.getLanguage());
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void reject(UUID id) throws NotFoundException {
        RoleRequest roleRequest = roleRequestRepository.findById(id).orElseThrow(() -> new NotFoundException(RoleRequestMessages.ROLE_REQUEST_NOT_FOUND, ErrorCodes.ROLE_REQUEST_NOT_FOUND));
        User user = roleRequest.getTenant().getUser();
        roleRequestRepository.delete(roleRequest);
        molEmailService.sendRoleRequestRejectedEmail(user.getEmail(), user.getFirstName(), user.getLanguage());
    }
}
