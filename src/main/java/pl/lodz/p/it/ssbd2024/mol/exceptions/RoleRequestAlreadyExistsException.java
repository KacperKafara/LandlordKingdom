package pl.lodz.p.it.ssbd2024.mol.exceptions;

import pl.lodz.p.it.ssbd2024.exceptions.ApplicationBaseException;

public class RoleRequestAlreadyExistsException extends ApplicationBaseException {
    public RoleRequestAlreadyExistsException(String message, String code) {
        super(message, code);
    }

    public RoleRequestAlreadyExistsException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
