package com.microservice.usermanagement.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private LocalDateTime timestamp;
    private final String detail;
    private final HttpStatus codigo;

    public CustomException(String message, HttpStatus httpStatus) {
        this.timestamp = timestamp;
        this.detail = message;
        this.codigo = httpStatus;
    }
    @Override
    public String getMessage() {
        return detail;
    }
    public HttpStatus getHttpStatus() {
        return codigo;
    }
}
