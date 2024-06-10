package pl.lodz.p.it.ssbd2024.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class DateUtils {
    private DateUtils() {
    }

    public static ZonedDateTime convertUTCToAnotherTimezoneFull(LocalDateTime dateTime, String timezone) throws DateTimeException {
        ZoneId destZoneId = ZoneId.of(timezone);

        return dateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(destZoneId);
    }

    public static String convertUTCToAnotherTimezoneSimple(LocalDateTime dateTime, String timezone, String lang) throws DateTimeException {
        Locale locale = Locale.of(lang);
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);

        ZonedDateTime longDate = convertUTCToAnotherTimezoneFull(dateTime, timezone);

        return longDate.format(formatter);
    }

    public static LocalDate getFirstDayOfCurrentWeek() {
        DayOfWeek firstDayOfWeek = DayOfWeek.SUNDAY;
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
    }

    public static LocalDate getLastDayOfCurrentWeek() {
        DayOfWeek firstDayOfWeek = DayOfWeek.SUNDAY;
        DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
    }

}
