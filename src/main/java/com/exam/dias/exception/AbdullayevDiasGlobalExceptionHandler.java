package com.exam.dias.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AbdullayevDiasGlobalExceptionHandler {

    @ExceptionHandler(AbdullayevDiasResourceNotFoundException.class)
    public ResponseEntity<AbdullayevDiasErrorResponse> handleResourceNotFound(AbdullayevDiasResourceNotFoundException ex) {
        AbdullayevDiasErrorResponse error = new AbdullayevDiasErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AbdullayevDiasUnauthorizedException.class)
    public ResponseEntity<AbdullayevDiasErrorResponse> handleUnauthorized(AbdullayevDiasUnauthorizedException ex) {
        AbdullayevDiasErrorResponse error = new AbdullayevDiasErrorResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AbdullayevDiasErrorResponse> handleGenericException(Exception ex) {
        AbdullayevDiasErrorResponse error = new AbdullayevDiasErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
