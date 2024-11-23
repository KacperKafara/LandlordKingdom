package pl.lodz.p.it.ssbd2024.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.ssbd2024.mol.repositories.AddressRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/health")
public class LivenessReadinessController {

    private final AddressRepository addressRepository;

    @GetMapping("/liveness")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> liveness(@RequestHeader("X-Application-Status") String header) {
        if (!header.equals("kubernetes")) {
            return ResponseEntity.status(503).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/readiness")
    @PreAuthorize("permitAll()")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Void> readiness(@RequestHeader("X-Application-Status") String header) {
        if (!header.equals("kubernetes")) {
            return ResponseEntity.status(503).build();
        }
        try {
            addressRepository.ping();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(503).build();
        }
    }

    @GetMapping("/hpa")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> hpa(@RequestParam("duration") int durationInSeconds) {
        long endTime = System.currentTimeMillis() + (durationInSeconds * 1000);
        
        while (System.currentTimeMillis() < endTime) {
            double result = 0.0;
            for (long i = 0; i < 1_000_000L; i++) {
                result += Math.sin(i) * Math.cos(i);
            }
        }

        return ResponseEntity.ok().build();
    }
}
