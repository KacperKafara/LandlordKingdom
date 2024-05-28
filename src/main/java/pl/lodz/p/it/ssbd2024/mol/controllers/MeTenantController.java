package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.mol.dto.*;
import pl.lodz.p.it.ssbd2024.mol.services.RentService;
import pl.lodz.p.it.ssbd2024.mol.services.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class MeTenantController {
    private final RoleService roleService;
    private final RentService rentService;

    @PostMapping("/getRole")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<GetRoleResponse> getRole() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/rents")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<List<RentResponse>> getRents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/rents/{id}")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<List<RentResponse>> getRent(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/rents/archival")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<List<RentResponse>> getArchivalRents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/applications")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<List<ApplicationResponse>> getApplications() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/rents/{id}/variableFee")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<VariableFeeResponse> enterVariableFee(@PathVariable UUID id, @RequestBody VariableFeeRequest variableFeeRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
