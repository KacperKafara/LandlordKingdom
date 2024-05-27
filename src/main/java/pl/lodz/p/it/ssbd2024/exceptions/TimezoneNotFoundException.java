package pl.lodz.p.it.ssbd2024.exceptions;

public class TimezoneNotFoundException extends ApplicationBaseException {
    public TimezoneNotFoundException(String message, String code) {
        super(message, code);
    }

    public TimezoneNotFoundException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
