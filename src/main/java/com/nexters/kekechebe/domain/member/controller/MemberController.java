package com.nexters.kekechebe.domain.member.controller;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexters.kekechebe.domain.member.service.MemberService;
import com.nexters.kekechebe.dto.BaseResponse;
import com.nexters.kekechebe.exceptions.StatusCode;
import com.nexters.kekechebe.jwt.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/kakao/callback")
    public ResponseEntity<BaseResponse> kakaoLogin(@RequestParam(value = "code") String code, HttpServletResponse response) throws
        JsonProcessingException {

        String createToken = memberService.kakaoLogin(code, response);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        // ResponseCookie rfCookie = ResponseCookie.from(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7))
        //     .path("/")
        //     .httpOnly(false)
        //     .secure(false)
        //     .sameSite("none")
        //     .build();

        return ResponseEntity.ok(new BaseResponse(StatusCode.OK));
    }
}
