package pl.lodz.p.it.ssbd2024.exceptions;

public class ApplicationOptimisticLockException extends ApplicationBaseException {
        public ApplicationOptimisticLockException(String message, String code) {
            super(message, code);
        }

        public ApplicationOptimisticLockException(String message, Throwable cause, String code) {
            super(message, cause, code);
        }
}
