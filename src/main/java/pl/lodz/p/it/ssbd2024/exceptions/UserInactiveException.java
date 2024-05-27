package pl.lodz.p.it.ssbd2024.exceptions;

public class UserInactiveException extends ApplicationBaseException{
    public UserInactiveException(String message, String code) {
        super(message, code);
    }

    public UserInactiveException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
