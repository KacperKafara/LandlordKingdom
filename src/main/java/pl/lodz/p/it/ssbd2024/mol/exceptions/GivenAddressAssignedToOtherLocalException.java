package pl.lodz.p.it.ssbd2024.mol.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GivenAddressAssignedToOtherLocalException extends Exception {
    public GivenAddressAssignedToOtherLocalException(String message) {
        super(message);
    }

    public GivenAddressAssignedToOtherLocalException(String message, Throwable cause) {
        super(message, cause);
    }
}
