package pl.lodz.p.it.ssbd2024.mol.exceptions;

import pl.lodz.p.it.ssbd2024.exceptions.ApplicationBaseException;

public class WrongEndDateException extends ApplicationBaseException {
    public WrongEndDateException(String message, String code) {
        super(message, code);
    }

    public WrongEndDateException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
