package com.nexters.kekechebe.domain.memo.controller;

import com.nexters.kekechebe.domain.memo.dto.request.MemoCreateRequest;
import com.nexters.kekechebe.domain.memo.dto.request.MemoUpdateRequest;
import com.nexters.kekechebe.domain.memo.dto.response.MemoPage;
import com.nexters.kekechebe.domain.memo.service.MemoService;
import com.nexters.kekechebe.dto.BaseResponse;
import com.nexters.kekechebe.dto.DataResponse;
import com.nexters.kekechebe.exceptions.StatusCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/memo")
@RequiredArgsConstructor
public class MemoController {
    private final MemoService memoService;

    @PostMapping()
    public ResponseEntity<BaseResponse> saveMemo(@RequestBody MemoCreateRequest request, HttpServletRequest httpServletRequest) {
//        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString()); 회원 구현에 따라 변경 예정
        long memberId = 1L;
        memoService.saveMemo(memberId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(StatusCode.CREATED));
    }

    @GetMapping
    public ResponseEntity<DataResponse<MemoPage>> getAllMemos(
            @PageableDefault(size=20, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest httpServletRequest
    ) {
//        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString()); 회원 구현에 따라 변경 예정
        long memberId = 1L;
        MemoPage memoPage = memoService.getAllMemos(memberId, pageable);

        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, memoPage));
    }

    @GetMapping("/character/{characterId}")
    public ResponseEntity<DataResponse<MemoPage>> getCharacterMemos(
            @PathVariable("characterId") long characterId,
            @PageableDefault(size=20, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            HttpServletRequest httpServletRequest
    ) {
//        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString()); 회원 구현에 따라 변경 예정
        long memberId = 1L;
        MemoPage characterMemoPage = memoService.getCharacterMemos(memberId, characterId, pageable);

        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, characterMemoPage));
    }

    @PutMapping("/{memoId}")
    public ResponseEntity<BaseResponse> updateMemo(@PathVariable("memoId") long memoId, @RequestBody MemoUpdateRequest request, HttpServletRequest httpServletRequest) {
//        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString()); 회원 구현에 따라 변경 예정
        long memberId = 1L;
        memoService.updateMemo(memberId, memoId, request);

        return ResponseEntity.ok(new BaseResponse(StatusCode.OK));
    }

    @DeleteMapping("/{memoId}")
    public ResponseEntity<BaseResponse> deleteMemo(@PathVariable("memoId") long memoId, HttpServletRequest httpServletRequest) {
//        long memberId = Long.parseLong(httpServletRequest.getAttribute("memberId").toString()); 회원 구현에 따라 변경 예정
        long memberId = 1L;
        memoService.deleteMemo(memberId, memoId);

        return ResponseEntity.ok(new BaseResponse(StatusCode.OK));
    }
}
