package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.messages.RentExceptionMessages;
import pl.lodz.p.it.ssbd2024.mol.dto.PaymentsAndFeesRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.RentFixedFeesResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.RentPaymentsResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.RentVariableFeesResponse;
import pl.lodz.p.it.ssbd2024.mol.mappers.FixedFeeMapper;
import pl.lodz.p.it.ssbd2024.mol.mappers.PaymentMapper;
import pl.lodz.p.it.ssbd2024.mol.mappers.VariableFeeMapper;
import pl.lodz.p.it.ssbd2024.mol.services.FixedFeeService;
import pl.lodz.p.it.ssbd2024.mol.services.PaymentService;
import pl.lodz.p.it.ssbd2024.mol.services.VariableFeeService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@RestController
@RequestMapping("/rents")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class RentController {
    private final PaymentService paymentService;
    private final VariableFeeService variableFeeService;
    private final FixedFeeService fixedFeeService;

    @PostMapping("/{id}/payments")
    @PreAuthorize("hasAnyRole('OWNER', 'TENANT')")
    public ResponseEntity<RentPaymentsResponse> getRentPayments(@PathVariable UUID id,
                                                                @RequestBody PaymentsAndFeesRequest rentPaymentsRequest,
                                                                @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("date").descending());
        UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
        try {
            LocalDate startDate = LocalDate.parse(rentPaymentsRequest.startDate());
            LocalDate endDate = LocalDate.parse(rentPaymentsRequest.endDate());
            return ResponseEntity.ok(PaymentMapper.toRentPaymentsResponse(paymentService.getRentPayments(id, userId, startDate, endDate, pageable)));
        } catch (DateTimeParseException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    RentExceptionMessages.DATE_PARSING_ERROR,
                    exception);
        }

    }

    @PostMapping("/{id}/fixed-fees")
    @PreAuthorize("hasAnyRole('OWNER', 'TENANT')")
    public ResponseEntity<RentFixedFeesResponse> getFixedFees(@PathVariable UUID id,
                                                              @RequestBody PaymentsAndFeesRequest rentPaymentsRequest,
                                                              @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("date").descending());
        UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
        try {
            LocalDate startDate = LocalDate.parse(rentPaymentsRequest.startDate());
            LocalDate endDate = LocalDate.parse(rentPaymentsRequest.endDate());
            return ResponseEntity.ok(FixedFeeMapper.toRentFixedFeesResponse(fixedFeeService.getRentFixedFees(id, userId, startDate, endDate, pageable)));
        } catch (DateTimeParseException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    RentExceptionMessages.DATE_PARSING_ERROR,
                    exception);
        }

    }

    @PostMapping("/{id}/variable-fees")
    @PreAuthorize("hasAnyRole('OWNER', 'TENANT')")
    public ResponseEntity<RentVariableFeesResponse> getVariableFees(@PathVariable UUID id,
                                                                    @RequestBody PaymentsAndFeesRequest rentPaymentsRequest,
                                                                    @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                                                    @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("date").descending());
        UUID userId = UUID.fromString(((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getSubject());
        try {
            LocalDate startDate = LocalDate.parse(rentPaymentsRequest.startDate());
            LocalDate endDate = LocalDate.parse(rentPaymentsRequest.endDate());
            return ResponseEntity.ok(VariableFeeMapper.toRentVariableFeesResponse(variableFeeService.getRentVariableFees(id, userId, startDate, endDate, pageable)));
        } catch (DateTimeParseException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    RentExceptionMessages.DATE_PARSING_ERROR,
                    exception);
        }
    }
}
