package com.nexters.kekechebe.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ExceptionResponse {
    private final int code;
    private final String message;

    public static ResponseEntity<ExceptionResponse> toResponseEntity(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(ExceptionResponse.builder()
                        .code(status.value())
                        .message(message).build());
    }
}
