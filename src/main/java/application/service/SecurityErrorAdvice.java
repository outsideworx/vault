package application.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
final class SecurityErrorAdvice {
    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<String> handle(AuthenticationException ae) {
        return new ResponseEntity<>(ae.getMessage(), HttpStatus.CONFLICT);
    }
}