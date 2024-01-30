package com.nexters.kekechebe.domain.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.kekechebe.domain.member.service.MemberService;
import com.nexters.kekechebe.dto.BaseResponse;
import com.nexters.kekechebe.dto.DataResponse;
import com.nexters.kekechebe.exceptions.StatusCode;
import com.nexters.kekechebe.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<BaseResponse> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        long memberId = userDetails.getMember().getId();
        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, memberService.getMemberInfo(memberId)));
    }
}