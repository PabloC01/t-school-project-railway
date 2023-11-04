package com.tsystems.pablo_canton.railway.setup.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Log4j2
@RestControllerAdvice
public class BusinessExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(DepartureTimeTooLateException.class)
    public ResponseEntity<?> handleDepartureTimeTooLate(DepartureTimeTooLateException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(SeatNotFreeException.class)
    public ResponseEntity<?> handleSeatNotFree(SeatNotFreeException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyHaveTicketException.class)
    public ResponseEntity<?> handleUserAlreadyHaveTicket(UserAlreadyHaveTicketException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(408).body(e.getMessage());
    }

    @ExceptionHandler(UserIsNotClientException.class)
    public ResponseEntity<?> handleUserIsNotClient(UserIsNotClientException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<?> handlePasswordIncorrect(PasswordIncorrectException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExists(UsernameAlreadyExistsException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(409).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknown(Exception e) {
        String errorId = UUID.randomUUID().toString();
        log.error(errorId, e.getMessage());
        return ResponseEntity.status(500).body("Server error occurred, please contact the admin. Error ID is " + errorId);
    }
}
