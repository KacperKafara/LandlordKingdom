package pl.lodz.p.it.ssbd2024.exceptions;

public class InvalidPasswordException extends ApplicationBaseException {

    public InvalidPasswordException(String message, String code) {
        super(message, code);
    }

    public InvalidPasswordException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
