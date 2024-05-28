package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.model.Timezone;
import pl.lodz.p.it.ssbd2024.mok.repositories.TimezoneRepository;
import pl.lodz.p.it.ssbd2024.mok.services.TimezoneService;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
@RequiredArgsConstructor
public class TimezoneServiceImpl implements TimezoneService {
    private final TimezoneRepository timezoneRepository;

    @Override
    @PreAuthorize("isAuthenticated()")
    public Timezone findByTimezoneName(String timezoneName) throws NotFoundException {
        return timezoneRepository.findByName(timezoneName).orElseThrow(() ->
                new NotFoundException("Timezone not found", ErrorCodes.TIMEZONE_NOT_FOUND));
    }
}
