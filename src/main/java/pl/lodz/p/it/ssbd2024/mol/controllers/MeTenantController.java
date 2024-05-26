package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.ssbd2024.mol.dto.GetRoleResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.RentResponse;
import pl.lodz.p.it.ssbd2024.mol.dto.VariableFeeRequest;
import pl.lodz.p.it.ssbd2024.mol.services.RentService;
import pl.lodz.p.it.ssbd2024.mol.services.RoleService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class MeTenantController {
    private final RoleService roleService;
    private final RentService rentService;

    @PostMapping("/getRole")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<GetRoleResponse> getRole() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("/rents")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<List<RentResponse>> getRents() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @PostMapping("/rents/{id}/payment")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity<RentResponse> enterVariableFee(@PathVariable UUID id, @RequestBody VariableFeeRequest variableFeeRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
