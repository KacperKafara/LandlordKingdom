package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.exceptions.InvalidLocalState;
import pl.lodz.p.it.ssbd2024.mol.dto.*;
import pl.lodz.p.it.ssbd2024.mol.mappers.LocalMapper;
import pl.lodz.p.it.ssbd2024.mol.services.LocalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/locals")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class LocalController {
    private final LocalService localService;

    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LocalResponse>> getActiveLocals() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/unapproved")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<GetAllLocalsResponse>> getUnapprovedLocals() {
        List<Local> unapprovedLocals = localService.getUnapprovedLocals();
        return ResponseEntity.ok(unapprovedLocals.stream().map(LocalMapper::toGetAllLocalsResponse).toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AddLocalResponse> addLocal(@RequestBody AddLocalRequest addLocalRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/applications")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<ApplicationResponse> addApplicationForLocal(@RequestBody ApplicationRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @DeleteMapping("/applications")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<Void> deleteApplicationForLocal(@RequestBody ApplicationRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LocalResponse> approveLocal(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LocalResponse> rejectLocal(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<GetAllLocalsResponse>> getAllLocals() {
        return ResponseEntity.ok(LocalMapper.toGetAllLocalsResponseList(localService.getAllLocals()));
    }

    @PatchMapping("/{id}/address")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<EditLocalResponse> changeLocalAddress(@PathVariable UUID id, @RequestBody EditLocalAddressRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<EditLocalResponse> editLocal(@PathVariable UUID id, @RequestBody EditLocalRequest editLocalRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PatchMapping("/{id}/archive")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LocalForAdministratorResponse> archiveLocal(@PathVariable UUID id) {
        try {
            Local archivedLocal = localService.archiveLocal(id);
            return ResponseEntity.ok(LocalMapper.toLocalForAdministratorResponse(archivedLocal));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InvalidLocalState e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LocalDetailsForAdminResponse> getLocal(@PathVariable UUID id) {
        try {
            Local local = localService.getLocal(id);
            return ResponseEntity.ok(LocalMapper.toLocalDetailsForAdminResponse(local));
        } catch (NotFoundException e) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
