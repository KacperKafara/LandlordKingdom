package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalPaymentsResponse;
import pl.lodz.p.it.ssbd2024.mol.services.PaymentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Scope("prototype")
@Transactional(propagation = Propagation.NEVER)
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/local/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<LocalPaymentsResponse>> getLocalPayments(@PathVariable UUID id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
