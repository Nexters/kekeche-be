package com.nexters.kekechebe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import jakarta.persistence.NoResultException;

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

    @ExceptionHandler(value = {IllegalStateException.class})
    protected ResponseEntity<ExceptionResponse> handleException(IllegalStateException e) {
        return ExceptionResponse.toResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e) {
        return ExceptionResponse.toResponseEntity(HttpStatus.BAD_REQUEST,
                Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(value = {NoResultException.class})
    protected ResponseEntity<ExceptionResponse> handleException(NoResultException e) {
        return ExceptionResponse.toResponseEntity(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionResponse> handleException(Exception e) {
        return ExceptionResponse.toResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
