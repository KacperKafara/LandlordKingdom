package pl.lodz.p.it.ssbd2024.exceptions;

public class UserAlreadyUnblockedException extends RuntimeException {
    public UserAlreadyUnblockedException(String message) {
        super(message);
    }
}
