package pl.lodz.p.it.ssbd2024.services.impl;

import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssbd2024.services.TimeService;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Service
public class TimeServiceImpl implements TimeService {

    @Override
    public ZonedDateTime convertUTCToAnotherTimezoneFull(LocalDateTime dateTime, String timezone) throws DateTimeException {
        ZoneId destZoneId = ZoneId.of(timezone);
        ZonedDateTime zonedDateTime = dateTime.atZone(destZoneId);

        return zonedDateTime;
    }

    @Override
    public String convertUTCToAnotherTimezoneSimple(LocalDateTime dateTime, String timezone, String lang) throws DateTimeException {
        Locale locale = Locale.of(lang);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);

        ZonedDateTime longDate = convertUTCToAnotherTimezoneFull(dateTime, timezone);
        String shortDate = longDate.format(formatter);

        return shortDate;
    }
}
