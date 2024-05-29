package pl.lodz.p.it.ssbd2024.mok.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.mok.dto.UserFilterResponse;
import pl.lodz.p.it.ssbd2024.mok.mappers.UserFilterMapper;
import pl.lodz.p.it.ssbd2024.mok.services.UserFilterService;

@RequestMapping("/filter")
@Scope("prototype")
@RestController
@RequiredArgsConstructor
public class FilterController {
    private final UserFilterService userFilterService;

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<UserFilterResponse> getFilterForCurrentUser() {
        try {
            return ResponseEntity.ok(UserFilterMapper.toUserFilterResponse(userFilterService.getFilterForCurrentUser()));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
