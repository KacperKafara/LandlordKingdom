package pl.lodz.p.it.ssbd2024.exceptions.handlers;

import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.exceptions.TemplateInputException;
import pl.lodz.p.it.ssbd2024.messages.ExceptionMessages;

import java.sql.SQLException;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ExceptionResponse> handleConstraintViolationException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error: e.getFieldErrors()) {
            sb.append(error.getDefaultMessage()).append(", ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(ExceptionMessages.VALIDATION_ERROR + sb));
    }

    @ExceptionHandler(TemplateInputException.class)
    ResponseEntity<ExceptionResponse> handleEmailTemplateException(TemplateInputException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.TEMPLATE_ERROR));
    }

    @ExceptionHandler(GenericJDBCException.class)
    ResponseEntity<ExceptionResponse> handleJDBCException(GenericJDBCException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.JDBC_ERROR));
    }

    @ExceptionHandler(JwtValidationException.class)
    ResponseEntity<ExceptionResponse> handleJwtValidationException(JwtValidationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(ExceptionMessages.INVALID_TOKEN));
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<ExceptionResponse> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(new ExceptionResponse(e.getReason()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ResponseEntity<ExceptionResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(new ExceptionResponse(ExceptionMessages.MEDIA_NOT_SUPPORTED));
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(ExceptionMessages.ACCESS_DENIED));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.UNCAUGHT));
    }

    @ExceptionHandler(OptimisticLockException.class)
    ResponseEntity<ExceptionResponse> handleOptimisticLockException(OptimisticLockException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(ExceptionMessages.OPTIMISTIC_LOCK));
    }

    @ExceptionHandler(PersistenceException.class)
    ResponseEntity<ExceptionResponse> handlePersistenceException(PersistenceException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.PERSISTENCE_ERROR));
    }

    @ExceptionHandler(SQLException.class)
    ResponseEntity<ExceptionResponse> handleSQLException(SQLException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(ExceptionMessages.JDBC_ERROR));
    }
}
