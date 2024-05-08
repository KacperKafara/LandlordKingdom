package pl.lodz.p.it.ssbd2024.mok.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.mok.services.AdministratorService;

import java.util.UUID;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

    @PutMapping(path = "/{id}/remove-role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> removeAccessLevel(@PathVariable UUID id) {
        try {
            administratorService.removeAdministratorAccessLevel(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}/add-role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> addAccessLevel(@PathVariable UUID id) {
        try {
            administratorService.addAdministratorAccessLevel(id);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }
}
