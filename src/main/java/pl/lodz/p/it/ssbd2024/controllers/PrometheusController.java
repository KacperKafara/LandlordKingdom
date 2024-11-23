package pl.lodz.p.it.ssbd2024.controllers;

import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prometheus")
@PreAuthorize("permitAll()")
public class PrometheusController {

    private final PrometheusMeterRegistry prometheusMeterRegistry;

    @GetMapping
    public ResponseEntity<String> scrapeMetrics() {
        String metrics = prometheusMeterRegistry.scrape();
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain; version=0.0.4; charset=utf-8")
                .body(metrics);
    }
}
