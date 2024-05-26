package pl.lodz.p.it.ssbd2024.services;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface TimeService {

    ZonedDateTime convertUTCToAnotherTimezoneFull(LocalDateTime dateTime, String timezone) throws DateTimeException;

    String convertUTCToAnotherTimezoneSimple(LocalDateTime dateTime, String timezone, String lang) throws DateTimeException;
}
