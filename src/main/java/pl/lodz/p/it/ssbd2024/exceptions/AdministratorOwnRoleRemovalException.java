package pl.lodz.p.it.ssbd2024.exceptions;

public class AdministratorOwnRoleRemovalException extends ApplicationBaseException {
    public AdministratorOwnRoleRemovalException(String message, String code) {
        super(message, code);
    }

    public AdministratorOwnRoleRemovalException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }
}
