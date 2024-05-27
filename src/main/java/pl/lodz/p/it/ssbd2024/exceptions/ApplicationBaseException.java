package pl.lodz.p.it.ssbd2024.exceptions;

import lombok.Getter;

@Getter
public abstract class ApplicationBaseException extends Exception {
    private final String code;

    public ApplicationBaseException(String message, String code) {
        super(message);
        this.code = code;
    }

    public ApplicationBaseException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }
}
