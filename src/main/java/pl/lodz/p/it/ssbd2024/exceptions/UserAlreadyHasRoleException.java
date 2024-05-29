package pl.lodz.p.it.ssbd2024.exceptions;

public class UserAlreadyHasRoleException extends ApplicationBaseException {

    public UserAlreadyHasRoleException(String message, String code) {
        super(message, code);
    }

    public UserAlreadyHasRoleException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
