package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.TimezoneNotFoundException;
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
    public Timezone findByTimezoneName(String timezoneName) throws TimezoneNotFoundException {
        return timezoneRepository.findByName(timezoneName).orElseThrow(() ->
                new TimezoneNotFoundException("Timezone not found", ErrorCodes.TIMEZONE_NOT_FOUND));
    }
}
