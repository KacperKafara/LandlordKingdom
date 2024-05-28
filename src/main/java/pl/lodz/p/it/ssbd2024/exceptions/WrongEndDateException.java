package pl.lodz.p.it.ssbd2024.exceptions;

public class WrongEndDateException extends ApplicationBaseException {
    public WrongEndDateException(String message, String code) {
        super(message, code);
    }

    public WrongEndDateException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
