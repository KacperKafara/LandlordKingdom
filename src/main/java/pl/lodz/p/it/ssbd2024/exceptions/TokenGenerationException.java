package pl.lodz.p.it.ssbd2024.exceptions;

import java.security.NoSuchAlgorithmException;

public class TokenGenerationException extends Exception {
    public TokenGenerationException(String message) {
        super(message);
    }
}
