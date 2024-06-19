package pl.lodz.p.it.ssbd2024.mol.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.ApplicationOptimisticLockException;
import pl.lodz.p.it.ssbd2024.exceptions.GivenAddressAssignedToOtherLocalException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.Address;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.model.Local;
import pl.lodz.p.it.ssbd2024.mol.dto.*;
import pl.lodz.p.it.ssbd2024.mol.mappers.AddressMapper;
import pl.lodz.p.it.ssbd2024.mol.mappers.LocalMapper;
import pl.lodz.p.it.ssbd2024.mol.mappers.ReportMapper;
import pl.lodz.p.it.ssbd2024.mol.services.LocalService;
import pl.lodz.p.it.ssbd2024.util.Signer;
import pl.lodz.p.it.ssbd2024.mol.services.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/locals")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class LocalController {
    private final LocalService localService;
    private final ReportService reportService;
    private final Signer signer;

    @GetMapping("/active")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GetActiveLocalsResponsePage> getActiveLocals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(LocalMapper.toGetAllActiveLocalsResponseList(localService.getActiveLocals(pageable)));
    }

    @GetMapping("/unapproved")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<GetUnapprovedLocalPageResponse> getUnapprovedLocals(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Local> unapprovedLocals = localService.getUnapprovedLocals(pageable);
        return ResponseEntity.ok(LocalMapper.toGetUnapprovedLocalPageResponse(unapprovedLocals));
    }

    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AddLocalResponse> addLocal(@RequestBody @Valid AddLocalRequest addLocalRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID userId = UUID.fromString(jwt.getSubject());
        try {
            Address address = new Address(
                    addLocalRequest.address().country(),
                    addLocalRequest.address().city(),
                    addLocalRequest.address().street(),
                    addLocalRequest.address().number(),
                    addLocalRequest.address().zipCode()
            );
            Local local = new Local(
                    addLocalRequest.name(),
                    addLocalRequest.description(),
                    addLocalRequest.size(),
                    address,
                    null,
                    addLocalRequest.marginFee(),
                    addLocalRequest.rentalFee()
            );
            return ResponseEntity.ok(LocalMapper.toGetAddLocalResponse(localService.addLocal(local, userId)));
        } catch (GivenAddressAssignedToOtherLocalException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (CreationException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }

    @PatchMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LocalResponse> approveLocal(@PathVariable UUID id) {
        try {
            localService.approveLocal(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InvalidLocalState e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LocalResponse> rejectLocal(@PathVariable UUID id) {
        try {
            localService.rejectLocal(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (InvalidLocalState e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<GetAllLocalsFiltered> getAllLocals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "ALL") String state,
            @RequestParam(defaultValue = "") String ownerLogin
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(LocalMapper.toGetAllLocalsFiltered(localService.getAllLocals(pageable, state, ownerLogin)));
    }

    @PatchMapping("/{id}/address")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<LocalForAdministratorResponse> changeLocalAddress(@PathVariable UUID id,
                                                                            @Valid @RequestBody EditLocalAddressRequest request,
                                                                            @RequestHeader(HttpHeaders.IF_MATCH) String tagValue){
        Address address = AddressMapper.editAddressRequestToAddress(request);
        try {
            Local local = localService.changeLocalAddress(id, address, tagValue);
            return ResponseEntity.ok(LocalMapper.toLocalForAdministratorResponse(local));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (GivenAddressAssignedToOtherLocalException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (ApplicationOptimisticLockException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
        } catch (InvalidLocalState e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<EditLocalResponse> editLocal(@PathVariable UUID id, @RequestHeader(HttpHeaders.IF_MATCH) String tagValue, @RequestBody @Valid EditLocalRequestAdmin editLocalRequest) {
        try {
            return ResponseEntity.ok(LocalMapper.toEditLocalResponse(localService.editLocalByAdmin(id, editLocalRequest, tagValue)));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (ApplicationOptimisticLockException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
        }
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
            return ResponseEntity
                    .ok()
                    .eTag(signer.generateSignature(local.getId(), local.getVersion()))
                    .body(LocalMapper.toLocalDetailsForAdminResponse(local));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/active/{id}")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<ActiveLocalResponse> getActiveLocal(@PathVariable UUID id) {
        try {
            Local local = localService.getActiveLocal(id);
            return ResponseEntity.ok(LocalMapper.toLocalPublicResponse(local));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}/report")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<LocalReportResponse> getLocalReport(
            @PathVariable UUID id,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) throws NotFoundException {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(
                ReportMapper.toLocalReportResponse(reportService.getLocalReport(id, userId, startDate, endDate))
        );
    }

    @GetMapping("/report")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<AllLocalsReportResponse> getReport() {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        return ResponseEntity.ok(ReportMapper.toAllLocalsReportResponse(reportService.getReport(userId)));
    }
}
