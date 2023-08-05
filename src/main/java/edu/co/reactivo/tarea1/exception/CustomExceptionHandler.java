package edu.co.reactivo.tarea1.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Arrays;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CustomErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(ex.getStatusCode().value(), Arrays.toString(ex.getStackTrace()),
                ex.getReason(), Instant.now().toEpochMilli());

        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }
}
