package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.RoleRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.RoleRequestResponse;
import pl.lodz.p.it.ssbd2024.mol.mappers.RoleRequestMapper;
import pl.lodz.p.it.ssbd2024.mol.services.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<RoleRequestResponse>> getRoleRequests() {
        List<RoleRequest> requests = roleService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(requests.stream().map(RoleRequestMapper::toRoleRequestResponse).toList());
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Retryable(retryFor = {RuntimeException.class})
    public ResponseEntity<Void> acceptRoleRequest(@PathVariable UUID id) {
        try {
            roleService.accept(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Retryable(retryFor = {RuntimeException.class})
    public ResponseEntity<Void> rejectRoleRequest(@PathVariable UUID id) {
        try {
            roleService.reject(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
