package com.example.backend.advise;

import com.example.backend.payload.response.MessageResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;
// Globalna obsługa wyjątków

@RestControllerAdvice
public class ValidationExceptionAdvice {

    // Wyjątek do walidacji danych
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errorMessages);
    }
    // Tworzenie encji, która już istnieje w bazie danych 409
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<MessageResponse> handleEntityExistsException(EntityExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse(ex.getMessage()));
    }
    // Nie znalezniono encji w bazie 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(ex.getMessage()));
    }
    // Nieprawidłowy argument (format danych) 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(ex.getMessage()));
    }
}