package com.nexters.kekechebe.domain.memo.controller;

import com.nexters.kekechebe.domain.character.dto.response.CharacterLevelUpResponse;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.memo.dto.request.MemoCreateRequest;
import com.nexters.kekechebe.domain.memo.dto.request.MemoUpdateRequest;
import com.nexters.kekechebe.domain.memo.dto.response.MemoPage;
import com.nexters.kekechebe.domain.memo.service.MemoService;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/memo")
@RequiredArgsConstructor
public class MemoController {
    private final MemoService memoService;

    @Operation(
            summary = "기록 저장",
            description = "회원이 쓴 기록을 저장합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "캐릭터 id, 기록 내용",
                    required = true
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "CREATED",
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
    @PostMapping()
    public ResponseEntity<BaseResponse> saveMemo(
            @RequestBody @Valid MemoCreateRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Member member = userDetails.getMember();
        CharacterLevelUpResponse characterLevelUp = memoService.saveMemo(member, request);

        return ResponseEntity.status(StatusCode.CREATED.getCode()).body(new DataResponse<>(StatusCode.CREATED, characterLevelUp));
    }

    @Operation(summary = "모든 기록 조회", description = "회원의 모든 기록을 조회합니다.")
    @Parameter(name = "page", description = "조회할 페이지(0부터 시작)", example = "0")
    @Parameter(name = "size", description = "조회할 기록 개수", example = "20")
    @Parameter(name = "sort", description = "정렬", example = "createdAt,DESC")
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
    public ResponseEntity<DataResponse<MemoPage>> getAllMemos(
            @Parameter(hidden = true) @PageableDefault(size=20, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        Member member = userDetails.getMember();
        MemoPage memoPage = memoService.getAllMemos(member, pageable);

        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, memoPage));
    }

    @Operation(summary = "캐릭터 기록 조회", description = "특정 캐릭터에 해당하는 기록을 조회합니다.")
    @Parameter(name = "characterId", description = "기록을 조회할 캐릭터의 id", example = "12", required = true)
    @Parameter(name = "page", description = "조회할 페이지(0부터 시작)", example = "0")
    @Parameter(name = "size", description = "조회할 기록 개수", example = "20")
    @Parameter(name = "sort", description = "정렬", example = "createdAt,DESC")
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
    @GetMapping("/character/{characterId}")
    public ResponseEntity<DataResponse<MemoPage>> getCharacterMemos(
            @PathVariable("characterId") long characterId,
            @Parameter(hidden = true) @PageableDefault(size=20, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        Member member = userDetails.getMember();
        MemoPage characterMemoPage = memoService.getCharacterMemos(member, characterId, pageable);

        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, characterMemoPage));
    }

    @Operation(
            summary = "기록 수정",
            description = "회원이 썼던 기록을 수정합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "기록 내용",
                    required = true
            )
    )
    @Parameter(name = "memoId", description = "수정할 기록의 id", example = "12", required = true)
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
    @PutMapping("/{memoId}")
    public ResponseEntity<BaseResponse> updateMemo(@PathVariable("memoId") long memoId, @RequestBody MemoUpdateRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        memoService.updateMemo(member, memoId, request);

        return ResponseEntity.ok(new BaseResponse(StatusCode.OK));
    }

    @Operation(summary = "기록 삭제", description = "회원이 썼던 기록을 삭제합니다.")
    @Parameter(name = "memoId", description = "삭제할 기록의 id", example = "12", required = true)
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
    @DeleteMapping("/{memoId}")
    public ResponseEntity<BaseResponse> deleteMemo(@PathVariable("memoId") long memoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        memoService.deleteMemo(member, memoId);

        return ResponseEntity.ok(new BaseResponse(StatusCode.OK));
    }

    @Operation(summary = "기록 검색", description = "회원이 기록을 검색합니다.")
    @Parameter(name = "page", description = "조회할 페이지(0부터 시작)", example = "0")
    @Parameter(name = "size", description = "조회할 기록 개수", example = "20")
    @Parameter(name = "sort", description = "정렬", example = "createdAt,DESC")
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
    @GetMapping("/search")
    public ResponseEntity<DataResponse<MemoPage>> search(
            @RequestParam("keyword") String keyword,
            @Parameter(hidden = true) @PageableDefault(size=20, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        Member member = userDetails.getMember();
        MemoPage memoPage = memoService.search(member, keyword, pageable);

        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, memoPage));
    }
}
