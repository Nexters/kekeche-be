package com.nexters.kekechebe.domain.auth.controller;

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

    @GetMapping("/kakao/callback")
    public ResponseEntity<BaseResponse> kakaoLogin(@RequestParam(value = "code") String code, HttpServletResponse response) {
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
}
