package pl.lodz.p.it.ssbd2024.mok.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.mok.services.TenantService;

import java.util.UUID;

@RestController
@RequestMapping("/tenants")
public class TenantController {

    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PutMapping(path = "/{id}/add-role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> addAccessLevel(@PathVariable UUID id) {
        try {
            tenantService.addTenantAccessLevel(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}/remove-role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> removeAccessLevel(@PathVariable UUID id) {
        try {
            tenantService.removeTenantAccessLevel(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
