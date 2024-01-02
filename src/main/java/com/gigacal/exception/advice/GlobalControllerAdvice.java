package com.gigacal.exception.advice;

import com.gigacal.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handle500Exception(final RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<?> handle409Exception(final RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(value = {EventNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handle404Exception(final RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(value = {ForbiddenActionException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<?> handle403Exception(final RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(value = {CalendarException.IncorrectDataProvided.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handle400Exception(final RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatusCode status,
                                                                  final WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
}
