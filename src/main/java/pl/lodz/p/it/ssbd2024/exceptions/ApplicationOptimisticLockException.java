package pl.lodz.p.it.ssbd2024.exceptions;

public class ApplicationOptimisticLockException extends Exception{
    public ApplicationOptimisticLockException(String message) {
        super(message);
    }
}
