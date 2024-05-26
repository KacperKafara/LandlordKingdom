package pl.lodz.p.it.ssbd2024.mol.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.ssbd2024.mol.dto.AddLocalRequest;
import pl.lodz.p.it.ssbd2024.mol.dto.AddLocalResponse;
import pl.lodz.p.it.ssbd2024.mol.services.LocalService;

@RestController
@RequestMapping("/locals")
@RequiredArgsConstructor
@Transactional(propagation = Propagation.NEVER)
public class LocalController {
    private final LocalService localService;

    @PostMapping
    public ResponseEntity<AddLocalResponse> addLocal(@RequestBody AddLocalRequest addLocalRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
