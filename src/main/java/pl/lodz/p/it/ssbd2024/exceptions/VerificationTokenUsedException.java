package pl.lodz.p.it.ssbd2024.exceptions;

public class VerificationTokenUsedException extends ApplicationBaseException{

    public VerificationTokenUsedException(String message, String code) {
        super(message, code);
    }

    public VerificationTokenUsedException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
