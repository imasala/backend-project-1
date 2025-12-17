package com.project.backend_project.error_handling;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle IllegalArgumentException (custom validations)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle DataIntegrityViolationException (e.g., unique constraint violations)
     @ExceptionHandler(DataIntegrityViolationException.class)
     public ResponseEntity<Map<String, String>> handleUniqueConstraint(DataIntegrityViolationException ex) {
        Map<String, String> error = new HashMap<>();

        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            error.put("error", "Email already exists");
        } else {
            error.put("error", "Data integrity violation");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

     // Handle any other unexpected exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Don't worry, be happy! An unexpected error occurred.");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Authentication failed");
        response.put("message", "Incorrect email or password");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // 401
}

// Handles AccessDeniedException
 @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "error", "FORBIDDEN",
                        "message", "You do not have permission to access this resource"
                ));
    }

}
