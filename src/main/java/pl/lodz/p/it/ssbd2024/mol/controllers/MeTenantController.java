package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.RentExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.RoleRequest;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.model.VariableFee;
import pl.lodz.p.it.ssbd2024.mol.dto.*;
import pl.lodz.p.it.ssbd2024.mol.mappers.*;
import pl.lodz.p.it.ssbd2024.mol.services.ApplicationService;
import pl.lodz.p.it.ssbd2024.mol.services.RentService;
import pl.lodz.p.it.ssbd2024.mol.services.RoleService;
import pl.lodz.p.it.ssbd2024.mol.services.VariableFeeService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class MeTenantController {
    private final RoleService roleService;
    private final RentService rentService;
    private final ApplicationService applicationService;
    private final VariableFeeService variableFeeService;

    @PostMapping("/role-request")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<GetRoleRequestResponse> requestRole() throws NotFoundException {
        try {
            RoleRequest roleRequest = roleService.requestRole();
            User user = roleRequest.getTenant().getUser();
            return ResponseEntity.ok(RoleRequestMapper.toRoleResponse(roleRequest, user.getTimezone(), user.getLanguage()));
        } catch (RoleRequestAlreadyExistsException | UserAlreadyHasRoleException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/role-request")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<GetRoleRequestResponse> getRoleRequest() throws NotFoundException {
        RoleRequest roleRequest = roleService.get();
        User user = roleRequest.getTenant().getUser();
        return ResponseEntity.ok(RoleRequestMapper.toRoleResponse(roleRequest, user.getTimezone(), user.getLanguage()));
    }

    @GetMapping("/current-rents")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<List<RentForTenantResponse>> getCurrentRents() {
        UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
        return ResponseEntity.ok(rentService.getCurrentTenantRents(userId).stream().map(RentMapper::rentForTenantResponse).toList());
    }

    @GetMapping("/rents/{id}")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<RentDetailedTenantResponse> getRent(@PathVariable UUID id) throws NotFoundException {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(RentMapper.rentDetailedTenantResponse(rentService.getTenantRent(id, userId)));
    }

    @GetMapping("/rents/archival")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<List<RentForTenantResponse>> getArchivalRents() {
        UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
        return ResponseEntity.ok(rentService.getArchivalRentsForTenant(userId).stream().map(RentMapper::rentForTenantResponse).toList());
    }

    @GetMapping("/applications")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<List<OwnApplicationResponse>> getApplications() throws NotFoundException {
        UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
        return ResponseEntity.ok(ApplicationMapper.toGetOwnApplications(applicationService.getUserApplications(userId)));
    }

    @PostMapping("/rents/{id}/variable-fee")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<VariableFeeResponse> enterVariableFee(@PathVariable UUID id, @RequestBody VariableFeeRequest variableFeeRequest)
            throws NotFoundException {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        try {
            VariableFee variableFee = variableFeeService.create(userId, id, variableFeeRequest.amount());
            return ResponseEntity.ok(VariableFeeMapper.variableFeeResponse(variableFee));
        } catch (VariableFeeAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}
