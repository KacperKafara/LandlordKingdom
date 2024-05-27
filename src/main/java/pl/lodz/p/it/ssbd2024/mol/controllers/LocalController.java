package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.mol.dto.*;
import pl.lodz.p.it.ssbd2024.mol.services.LocalService;

import java.util.List;

@RestController
@RequestMapping("/locals")
@RequiredArgsConstructor
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
    public ResponseEntity<List<LocalResponse>> getUnapprovedLocals() {
        throw new UnsupportedOperationException("Not supported yet.");
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

    @PatchMapping("/state")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LocalResponse> approveLocal(@RequestBody ApproveRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
