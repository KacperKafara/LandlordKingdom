package pl.lodz.p.it.ssbd2024.exceptions;

public class RefreshTokenExpiredException extends Exception {
    public RefreshTokenExpiredException(String msg) {
        super(msg);
    }
}
