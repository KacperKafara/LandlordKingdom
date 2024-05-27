package pl.lodz.p.it.ssbd2024.mol.exceptions;

import pl.lodz.p.it.ssbd2024.model.LocalState;

public class InvalidLocalState extends Exception {
    public InvalidLocalState(String message, LocalState required, LocalState current) {
        super(message);
    }
}
