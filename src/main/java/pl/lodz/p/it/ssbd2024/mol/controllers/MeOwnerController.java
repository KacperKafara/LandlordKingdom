package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.mol.dto.*;
import pl.lodz.p.it.ssbd2024.mol.services.ApplicationService;
import pl.lodz.p.it.ssbd2024.mol.services.LocalService;
import pl.lodz.p.it.ssbd2024.mol.services.RentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class MeOwnerController {
    private final LocalService localService;
    private final ApplicationService applicationService;
    private final RentService rentService;

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

    @PostMapping("/locals/{id}/rent")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AcceptApplicationResponse> acceptApplication(@PathVariable UUID id, @RequestBody AcceptApplicationRequest acceptApplicationRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/locals/{id}/reports")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<LocalReportResponse> getReportData(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PutMapping("/locals/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<EditLocalResponse> editLocal(@PathVariable UUID id, @RequestBody EditLocalRequest editLocalRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/rents/current")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<RentResponse>> getCurrentRents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/rents/{id}/payment")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<RentResponse> payRent(@PathVariable UUID id, @RequestBody NewPaymentRequest newPaymentRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PatchMapping("/locals/{id}/leave")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<LeaveLocalResponse> leaveLocal(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PatchMapping("/locals/{id}/fixedFee")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<SetFixedFeeResponse> setFixedFee(@PathVariable UUID id, @RequestBody SetFixedFeeRequest setFixedFeeRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PatchMapping("/rents/{id}/endDate")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<RentResponse> setEndDate(@PathVariable UUID id, @RequestBody SetEndDateRequest setEndDateRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/locals/reports")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<LocalReportResponse>> getAllReports() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
