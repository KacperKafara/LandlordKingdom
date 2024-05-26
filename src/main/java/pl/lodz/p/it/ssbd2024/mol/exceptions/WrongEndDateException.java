package pl.lodz.p.it.ssbd2024.mol.exceptions;

public class WrongEndDateException extends Exception {
    public WrongEndDateException(String message) {
        super(message);
    }

    public WrongEndDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
