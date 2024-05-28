package pl.lodz.p.it.ssbd2024.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LocalAlreadyRentedException extends ApplicationBaseException {
    public LocalAlreadyRentedException(String message, String code) {
        super(message, code);
    }

    public LocalAlreadyRentedException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
