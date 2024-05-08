package pl.lodz.p.it.ssbd2024.exceptions;

import lombok.Getter;

@Getter
public class IdenticalFieldValueException extends Exception {

    String constraintName;

    public IdenticalFieldValueException(String message, String constraintName) {
        super(message);
        this.constraintName = constraintName;
    }
}
