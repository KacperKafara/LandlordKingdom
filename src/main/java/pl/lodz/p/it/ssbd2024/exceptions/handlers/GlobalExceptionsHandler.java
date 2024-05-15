package pl.lodz.p.it.ssbd2024.exceptions.handlers;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.exceptions.TemplateInputException;


@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<String> handleConstraintViolationException(MethodArgumentNotValidException e) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error: e.getFieldErrors()) {
            sb.append(error.getDefaultMessage()).append(", ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not valid due to validation error(s): " + sb);
    }

    @ExceptionHandler(TemplateInputException.class)
    ResponseEntity<String> handleEmailTemplateException(TemplateInputException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred while trying to send email");
    }

    @ExceptionHandler(GenericJDBCException.class)
    ResponseEntity<String> handleJDBCException(GenericJDBCException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problem occurred while trying to connect to the database");
    }

    @ExceptionHandler(JwtValidationException.class)
    ResponseEntity<String> handleJwtValidationException(JwtValidationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }

}
