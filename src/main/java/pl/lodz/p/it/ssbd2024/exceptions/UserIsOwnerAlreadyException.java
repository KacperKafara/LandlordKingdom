package pl.lodz.p.it.ssbd2024.exceptions;

public class UserIsOwnerAlreadyException extends ApplicationBaseException {

    public UserIsOwnerAlreadyException(String message, String code) {
        super(message, code);
    }

    public UserIsOwnerAlreadyException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
