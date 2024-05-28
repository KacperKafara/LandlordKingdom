package pl.lodz.p.it.ssbd2024.mol.exceptions;

import pl.lodz.p.it.ssbd2024.exceptions.ApplicationBaseException;

public class InvalidRelationException extends ApplicationBaseException {
    public InvalidRelationException(String message, String code) {
        super(message, code);
    }

    public InvalidRelationException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
