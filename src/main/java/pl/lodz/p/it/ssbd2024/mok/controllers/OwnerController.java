package pl.lodz.p.it.ssbd2024.mok.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.ssbd2024.exceptions.AccessLevelAlreadyRemovedException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Owner;
import pl.lodz.p.it.ssbd2024.mok.services.OwnerService;

import java.util.UUID;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PutMapping(path = "/{id}/role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> removeAccessLevel(@PathVariable UUID id) {
        Owner owner;
        try {
            owner = ownerService.removeOwnerAccessLevel(id);
        } catch (AccessLevelAlreadyRemovedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}/add-role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> addAccessLevel(@PathVariable UUID id) throws NotFoundException {
        Owner owner = ownerService.addOwnerAccessLevel(id);

        return ResponseEntity.ok().build();
    }
}
