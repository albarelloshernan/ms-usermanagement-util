package com.microservice.usermanagement.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private LocalDateTime timestamp;
    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        this.timestamp = timestamp;
        this.message = message;
        this.httpStatus = httpStatus;
    }
    @Override
    public String getMessage() {
        return message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
