package pl.lodz.p.it.ssbd2024.exceptions;

public class UserBlockedException extends ApplicationBaseException {

    public UserBlockedException(String message, String code) {
        super(message, code);
    }

    public UserBlockedException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
