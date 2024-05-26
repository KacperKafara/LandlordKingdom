package pl.lodz.p.it.ssbd2024.mol.exceptions;

public class LocalIsNotInactiveException extends Exception {
    public LocalIsNotInactiveException(String message) {
        super(message);
    }

    public LocalIsNotInactiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
