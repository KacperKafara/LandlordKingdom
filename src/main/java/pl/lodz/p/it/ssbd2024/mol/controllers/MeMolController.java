package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.mol.dto.*;
import pl.lodz.p.it.ssbd2024.mol.services.ApplicationService;
import pl.lodz.p.it.ssbd2024.mol.services.LocalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeMolController {
    private final LocalService localService;
    private final ApplicationService applicationService;

    @GetMapping("/locals")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<GetOwnLocalsResponse>> getOwnLocals() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/locals/{id}/applications")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<GetOwnLocalApplicationsResponse>> getOwnLocalApplications(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/locals/rent")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AcceptApplicationResponse> acceptApplication(@RequestBody AcceptApplicationRequest acceptApplicationRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/locals/{id}/reports")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<LocalReportResponse> getReportData(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
