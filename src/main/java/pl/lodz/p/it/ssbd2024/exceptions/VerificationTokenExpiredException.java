package pl.lodz.p.it.ssbd2024.exceptions;

public class VerificationTokenExpiredException extends Exception{

    public VerificationTokenExpiredException(String message) {
        super(message);
    }
}
