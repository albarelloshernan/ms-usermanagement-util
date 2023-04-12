package com.microservice.usermanagement.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    @Schema(required = true, description = "Cuerpo del error")
    private final String message;

    @Schema(required = true, description = "Código de estado del error")
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
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
