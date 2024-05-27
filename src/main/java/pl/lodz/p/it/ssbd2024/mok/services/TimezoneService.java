package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.TimezoneNotFoundException;
import pl.lodz.p.it.ssbd2024.model.Timezone;

public interface TimezoneService {
    Timezone findByTimezoneName(String timezoneName) throws TimezoneNotFoundException;
}
