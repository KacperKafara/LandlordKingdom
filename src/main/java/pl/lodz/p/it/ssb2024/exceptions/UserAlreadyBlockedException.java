package pl.lodz.p.it.ssb2024.exceptions;

public class UserAlreadyBlockedException extends RuntimeException {
    public UserAlreadyBlockedException(String message) {
        super(message);
    }
}
