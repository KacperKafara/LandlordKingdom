package pl.lodz.p.it.ssbd2024.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class LivenessReadinessController {

    @GetMapping("/liveness")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> liveness() {
        return ResponseEntity.ok().build();
    }
}
