package pl.lodz.p.it.ssbd2024.exceptions;

public class LoginNotMatchToOTPException extends Exception {
    public LoginNotMatchToOTPException(String message) {
        super(message);
    }
}
