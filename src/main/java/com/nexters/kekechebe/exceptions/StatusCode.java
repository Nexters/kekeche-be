package com.nexters.kekechebe.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StatusCode {

    /**
     * 400 BAD REQUEST
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /**
     * 401 UNAUTHORIZED
     */

    /**
     * 403 FORBIDDEN
     */

    /**
     * 404 NOT FOUND
     */

    /**
     * 409 CONFLICT
     */

    /**
     * 200 OK
     */
    OK(HttpStatus.OK, "성공적으로 요청을 처리하였습니다."),

    /**
     * 201 CREATED
     */
    CREATED(HttpStatus.CREATED, "성공적으로 요청을 처리하였습니다.");

    private final HttpStatus code;
    private final String message;
}
