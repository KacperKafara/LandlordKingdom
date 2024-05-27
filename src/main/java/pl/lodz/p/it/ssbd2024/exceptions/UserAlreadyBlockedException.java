package pl.lodz.p.it.ssbd2024.exceptions;

public class UserAlreadyBlockedException extends ApplicationBaseException {

    public UserAlreadyBlockedException(String message, String code) {
        super(message, code);
    }


    public UserAlreadyBlockedException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
