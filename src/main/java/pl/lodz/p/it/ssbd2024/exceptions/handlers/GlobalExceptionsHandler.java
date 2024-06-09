package pl.lodz.p.it.ssbd2024.exceptions.handlers;

import com.atomikos.icatch.RollbackException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.exceptions.TemplateInputException;
import pl.lodz.p.it.ssbd2024.exceptions.ApplicationBaseException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.messages.ExceptionMessages;

import java.sql.SQLException;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ExceptionResponse> handleConstraintViolationException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error : e.getFieldErrors()) {
            sb.append(error.getDefaultMessage()).append(", ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(ExceptionMessages.VALIDATION_ERROR + sb, ErrorCodes.VALIDATION_ERROR));
    }

    @ExceptionHandler(TemplateInputException.class)
    ResponseEntity<ExceptionResponse> handleEmailTemplateException(TemplateInputException e) {
        log.error("Uncaught exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.TEMPLATE_ERROR, ErrorCodes.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(GenericJDBCException.class)
    ResponseEntity<ExceptionResponse> handleJDBCException(GenericJDBCException e) {
        log.error("Uncaught exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.JDBC_ERROR, ErrorCodes.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(JwtValidationException.class)
    ResponseEntity<ExceptionResponse> handleJwtValidationException(JwtValidationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(ExceptionMessages.INVALID_TOKEN, ErrorCodes.JWT_TOKEN_INVALID));
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException e) {
        if (e.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.UNCAUGHT, ErrorCodes.INTERNAL_SERVER_ERROR));
        }

        if (e.getCause() instanceof ApplicationBaseException ex) {
            return ResponseEntity.status(e.getStatusCode()).body(new ExceptionResponse(e.getReason(), ex.getCode()));
        }
        return ResponseEntity.status(e.getStatusCode()).body(new ExceptionResponse(e.getReason(), ErrorCodes.SOMETHING_WENT_WRONG));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ResponseEntity<ExceptionResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ExceptionResponse(ExceptionMessages.MEDIA_NOT_SUPPORTED, ErrorCodes.INVALID_DATA));
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(ExceptionMessages.ACCESS_DENIED, ErrorCodes.ACCESS_DENIED));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error("Uncaught exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.UNCAUGHT, ErrorCodes.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(OptimisticLockException.class)
    ResponseEntity<ExceptionResponse> handleOptimisticLockException(OptimisticLockException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(ExceptionMessages.OPTIMISTIC_LOCK, ErrorCodes.OPTIMISTIC_LOCK));
    }

    @ExceptionHandler(PersistenceException.class)
    ResponseEntity<ExceptionResponse> handlePersistenceException(PersistenceException e) {
        log.error("Uncaught exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.PERSISTENCE_ERROR, ErrorCodes.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(SQLException.class)
    ResponseEntity<ExceptionResponse> handleSQLException(SQLException e) {
        log.error("Uncaught exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.JDBC_ERROR, ErrorCodes.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(RollbackException.class)
    ResponseEntity<ExceptionResponse> handleRollbackException(RollbackException e) {
        log.error("Uncaught exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.ROLLBACK, ErrorCodes.ROLLBACK));
    }

    @ExceptionHandler(UnexpectedRollbackException.class)
    ResponseEntity<ExceptionResponse> handleUnexpectedRollbackException(UnexpectedRollbackException e) {
        log.error("Uncaught exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.UNEXPECTED_ROLLBACK, ErrorCodes.UNEXPECTED_ROLLBACK));
    }

    @ExceptionHandler(TransactionException.class)
    ResponseEntity<ExceptionResponse> handleTransactionException(TransactionException e) {
        log.error("Uncaught exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.TRANSACTION, ErrorCodes.TRANSACTION));
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(e.getMessage(), e.getCode()));
    }
}
