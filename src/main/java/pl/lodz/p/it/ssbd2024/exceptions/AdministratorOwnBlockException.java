package pl.lodz.p.it.ssbd2024.exceptions;

public class AdministratorOwnBlockException extends ApplicationBaseException {
    public AdministratorOwnBlockException(String message, String code) {
        super(message, code);
    }

    public AdministratorOwnBlockException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
