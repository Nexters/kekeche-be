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
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰 입니다."),
    JWT_CLAIMS_IS_EMPTY(HttpStatus.UNAUTHORIZED, "잘못된 JWT 토큰 입니다."),
    TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한 정보가 잘못된 토큰입니다."),

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
