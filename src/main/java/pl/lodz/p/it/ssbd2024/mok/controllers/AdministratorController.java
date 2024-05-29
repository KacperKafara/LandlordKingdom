package pl.lodz.p.it.ssbd2024.mok.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.AdministratorOwnRoleRemovalException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.mok.services.AdministratorService;

import java.util.UUID;

@RestController
@Scope("prototype")
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdministratorController {

    private final AdministratorService administratorService;

    @PutMapping(path = "/{id}/remove-role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> removeAccessLevel(@PathVariable UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID administratorId = UUID.fromString(jwt.getSubject());

        try {
            administratorService.removeAdministratorAccessLevel(id, administratorId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (AdministratorOwnRoleRemovalException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}/add-role")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> addAccessLevel(@PathVariable UUID id) {
        try {
            administratorService.addAdministratorAccessLevel(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return ResponseEntity.ok().build();
    }
}
