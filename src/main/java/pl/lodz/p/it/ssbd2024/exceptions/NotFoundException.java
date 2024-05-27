package pl.lodz.p.it.ssbd2024.exceptions;

public class NotFoundException extends ApplicationBaseException {
    public NotFoundException(String message, String code) {
        super(message, code);
    }

    public NotFoundException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }


}
