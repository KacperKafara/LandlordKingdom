package pl.lodz.p.it.ssb2024.exceptions;

public class UserAlreadyUnblockedException extends RuntimeException {
    public UserAlreadyUnblockedException(String message) {
        super(message);
    }
}
