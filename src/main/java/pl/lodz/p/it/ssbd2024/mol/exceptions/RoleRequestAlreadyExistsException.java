package pl.lodz.p.it.ssbd2024.mol.exceptions;

public class RoleRequestAlreadyExistsException extends Exception{
    public RoleRequestAlreadyExistsException(String message) {
        super(message);
    }

    public RoleRequestAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
