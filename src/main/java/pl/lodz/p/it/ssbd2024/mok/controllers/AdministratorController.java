package pl.lodz.p.it.ssbd2024.mok.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.exceptions.AccessLevelAlreadyRemovedException;
import pl.lodz.p.it.ssbd2024.model.Administrator;
import pl.lodz.p.it.ssbd2024.mok.services.AdministratorService;

import java.util.UUID;

@RestController
@RequestMapping("/admins")
public class AdministratorController {

    private final AdministratorService administratorService;

    @Autowired
    public AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @PutMapping(path = "/{id}/role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> removeAccessLevel(@PathVariable UUID id){
        Administrator administrator;
        try {
            administrator = administratorService.removeAdministratorAccessLevel(id);
        } catch (AccessLevelAlreadyRemovedException  e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}/add-role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> addAccessLevel(@PathVariable UUID id){
        Administrator administrator = administratorService.addAdministratorAccessLevel(id);

        return ResponseEntity.ok().build();
    }
}
