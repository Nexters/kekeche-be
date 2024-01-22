package com.nexters.kekechebe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ExceptionResponse> handleException(CustomException e) {
        return ExceptionResponse.toResponseEntity(e.getStatusCode().getCode(), e.getStatusCode().getMessage());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ExceptionResponse> handleException(IllegalArgumentException e) {
        return ExceptionResponse.toResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e) {
        return ExceptionResponse.toResponseEntity((HttpStatus)e.getStatusCode(),
                Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }
}
