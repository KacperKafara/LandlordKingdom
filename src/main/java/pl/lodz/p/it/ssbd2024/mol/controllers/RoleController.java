package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.mol.dto.RoleRequestResponse;
import pl.lodz.p.it.ssbd2024.mol.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<RoleRequestResponse>> getRoleRequests() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteRoleRequest() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
