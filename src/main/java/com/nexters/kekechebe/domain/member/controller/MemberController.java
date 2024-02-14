package com.nexters.kekechebe.domain.member.controller;

import com.nexters.kekechebe.domain.member.dto.response.MemberCheerResponse;
import com.nexters.kekechebe.domain.member.dto.response.MemberInfoResponse;
import com.nexters.kekechebe.domain.member.dto.response.MemberResponse;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.member.service.MemberService;
import com.nexters.kekechebe.dto.BaseResponse;
import com.nexters.kekechebe.dto.DataResponse;
import com.nexters.kekechebe.exceptions.ExceptionResponse;
import com.nexters.kekechebe.exceptions.StatusCode;
import com.nexters.kekechebe.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 정보 조회(id, nickname only)", description = "회원의 정보를 조회합니다.")
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
    @GetMapping("/info")
    public ResponseEntity<DataResponse<MemberResponse>> getMember(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, memberService.getMember(member)));
    }

    @Operation(summary = "회원 정보 조회", description = "회원의 정보를 조회합니다.")
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
    @GetMapping
    public ResponseEntity<DataResponse<MemberInfoResponse>> getMemberInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, memberService.getMemberInfo(member)));
    }

    @Operation(summary = "회원 응원하기", description = "회원을 응원합니다.")
    @Parameter(name = "memberId", description = "응원할 회원의 id", example = "12", required = true)
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
        @ApiResponse(responseCode = "404", description = "NOT FOUND",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/cheer/{memberId}")
    public ResponseEntity<DataResponse<MemberCheerResponse>> updateCheerCount(
        @PathVariable("memberId") Long memberId
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member loginMember = userDetails != null ? userDetails.getMember() : null;
        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, memberService.updateCheerCount(loginMember, memberId)));
    }

    @Operation(summary = "회원 탈퇴", description = "회원의 정보를 삭제하고 탈퇴시킵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteMember(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        memberService.deleteMember(member);

        return ResponseEntity.ok(new BaseResponse(StatusCode.OK));
    }
}