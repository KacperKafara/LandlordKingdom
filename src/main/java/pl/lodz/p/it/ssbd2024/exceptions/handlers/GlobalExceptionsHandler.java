package pl.lodz.p.it.ssbd2024.exceptions.handlers;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> handleConstraintViolationException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error: e.getFieldErrors()) {
            sb.append(error.getDefaultMessage()).append(", ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionMessages.VALIDATION_ERROR + sb);
    }

    @ExceptionHandler(TemplateInputException.class)
    ResponseEntity<String> handleEmailTemplateException(TemplateInputException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionMessages.TEMPLATE_ERROR);
    }

    @ExceptionHandler(GenericJDBCException.class)
    ResponseEntity<String> handleJDBCException(GenericJDBCException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionMessages.JDBC_ERROR);
    }

    @ExceptionHandler(JwtValidationException.class)
    ResponseEntity<String> handleJwtValidationException(JwtValidationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionMessages.INVALID_TOKEN);
    }

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<String> handleResponseStatusException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ResponseEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ExceptionMessages.MEDIA_NOT_SUPPORTED);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionMessages.UNCAUGHT);
    }
}
