package pl.lodz.p.it.ssbd2024.mol.exceptions;

public class UserIsOwnerAlreadyException extends Exception{
    public UserIsOwnerAlreadyException(String message) {
        super(message);
    }

    public UserIsOwnerAlreadyException(String message, Throwable cause) {
        super(message, cause);
    }
}
