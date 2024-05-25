package pl.lodz.p.it.ssbd2024.mol.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LocalAlreadyRentedException extends Exception {
    public LocalAlreadyRentedException(String message) {
        super(message);
    }

    public LocalAlreadyRentedException(String message, Throwable cause) {
        super(message, cause);
    }
}
