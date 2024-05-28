package pl.lodz.p.it.ssbd2024.mol.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.ssbd2024.exceptions.ApplicationBaseException;

@ResponseStatus(HttpStatus.CONFLICT)
public class GivenAddressAssignedToOtherLocalException extends ApplicationBaseException {
    public GivenAddressAssignedToOtherLocalException(String message, String code) {
        super(message, code);
    }

    public GivenAddressAssignedToOtherLocalException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
