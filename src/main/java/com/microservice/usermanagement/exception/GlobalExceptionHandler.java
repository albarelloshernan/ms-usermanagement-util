package com.microservice.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AccountException.class)
    public ResponseEntity<Object> exception(AccountException exception) {
        return new ResponseEntity<>("Account creation failed.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}