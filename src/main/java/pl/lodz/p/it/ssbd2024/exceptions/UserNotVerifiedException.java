package pl.lodz.p.it.ssbd2024.exceptions;

public class UserNotVerifiedException extends ApplicationBaseException {

    public UserNotVerifiedException(String message, String code) {
        super(message, code);
    }

    public UserNotVerifiedException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
