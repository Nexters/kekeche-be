package com.nexters.kekechebe.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StatusCode {

    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /**
     * 401 Unauthorized
     */

    /**
     * 403 FORBIDDEN
     */

    /**
     * 404 Not Found
     */

    /**
     * 409 Conflict
     */

    /**
     * 2xx OK
     */
    OK(HttpStatus.OK, "성공");

    private final HttpStatus code;
    private final String message;
}
