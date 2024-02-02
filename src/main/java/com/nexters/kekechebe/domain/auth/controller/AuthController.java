package com.nexters.kekechebe.domain.auth.controller;

import com.nexters.kekechebe.exceptions.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.kekechebe.domain.auth.service.AuthService;
import com.nexters.kekechebe.domain.auth.dto.response.LoginResponse;
import com.nexters.kekechebe.dto.BaseResponse;
import com.nexters.kekechebe.dto.DataResponse;
import com.nexters.kekechebe.exceptions.StatusCode;
import com.nexters.kekechebe.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "카카오 로그인", description = "카카오 로그인을 진행합니다.")
    @Parameter(name = "code", description = "카카오 인가 코드", example = "QTxoN3oYZqrOPLbXROVx-nAtvpDzk6ph-1Zip1Vf3kdLBiX49ASx9kkIdQIKPXNNAAABjVozDgQq3eF1vjqPRg", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping("/kakao/callback")
    public ResponseEntity<DataResponse<LoginResponse>> kakaoLogin(@RequestParam(value = "code") String code, HttpServletResponse response) {
        log.info("Auth Controller >> code : {}", code);

        LoginResponse loginResponse = authService.kakaoLogin(code, response);
        String createToken = loginResponse.getAccessToken();

        ResponseCookie rfCookie = ResponseCookie.from(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7))
            .path("/")
            .httpOnly(false)
            .secure(false)
            .sameSite("none")
            .build();
        response.setHeader("Set-Cookie", rfCookie.toString());

        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, loginResponse));
    }

    @Operation(summary = "캐릭터 생성 후 카카오 로그인", description = "캐릭터를 생성한 후 카카오 로그인을 진행합니다.")
    @Parameter(name = "code", description = "카카오 인가 코드", example = "QTxoN3oYZqrOPLbXROVx-nAtvpDzk6ph-1Zip1Vf3kdLBiX49ASx9kkIdQIKPXNNAAABjVozDgQq3eF1vjqPRg", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping("/character-kakao/callback")
    public ResponseEntity<DataResponse<LoginResponse>> characterKakaoLogin(@RequestParam(value = "code") String code, HttpServletResponse response) {
        log.info("Auth Controller >> code : {}", code);

        LoginResponse loginResponse = authService.characterKakaoLogin(code, response);
        String createToken = loginResponse.getAccessToken();

        ResponseCookie rfCookie = ResponseCookie.from(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7))
                .path("/")
                .httpOnly(false)
                .secure(false)
                .sameSite("none")
                .build();
        response.setHeader("Set-Cookie", rfCookie.toString());

        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, loginResponse));
    }
}
