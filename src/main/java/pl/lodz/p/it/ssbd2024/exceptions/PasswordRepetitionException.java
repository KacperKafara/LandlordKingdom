package pl.lodz.p.it.ssbd2024.exceptions;

public class PasswordRepetitionException extends ApplicationBaseException {

    public PasswordRepetitionException(String message, String code) {
        super(message, code);
    }

    public PasswordRepetitionException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
