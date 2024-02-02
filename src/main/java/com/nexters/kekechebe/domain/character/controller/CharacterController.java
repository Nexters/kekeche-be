package com.nexters.kekechebe.domain.character.controller;

import java.util.List;

import com.nexters.kekechebe.domain.character.dto.response.CharacterThumbnailResponse;
import com.nexters.kekechebe.exceptions.ExceptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.kekechebe.domain.character.dto.request.CharacterCreateRequest;
import com.nexters.kekechebe.domain.character.dto.request.CharacterNameUpdateRequest;
import com.nexters.kekechebe.domain.character.dto.response.CharacterIdResponse;
import com.nexters.kekechebe.domain.character.dto.response.CharacterListResponse;
import com.nexters.kekechebe.domain.character.dto.response.CharacterResponse;
import com.nexters.kekechebe.domain.character.service.CharacterService;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.dto.BaseResponse;
import com.nexters.kekechebe.dto.DataResponse;
import com.nexters.kekechebe.exceptions.StatusCode;
import com.nexters.kekechebe.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/character")
public class CharacterController {

    private final CharacterService characterService;

    @Operation(
            summary = "캐릭터 생성",
            description = "회원이 만든 캐릭터를 저장합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "캐릭터 정보 (이름, 색 id, 모양 id), 아이템 id, 키워드",
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
    @PostMapping
    public ResponseEntity<BaseResponse> saveCharacter(
        @RequestBody CharacterCreateRequest request
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        CharacterIdResponse characterIdResponse = characterService.saveCharacter(member, request);
        return ResponseEntity.status(StatusCode.CREATED.getCode()).body(new DataResponse<>(StatusCode.CREATED, characterIdResponse));
    }

    @Operation(summary = "특정 회원의 모든 캐릭터 조회", description = "특정 회원의 모든 캐릭터를 조회합니다.")
    @Parameter(name = "memberId", description = "조회할 회원의 id", example = "12", required = true)
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
    @GetMapping( "/member/{memberId}")
    public ResponseEntity<DataResponse<CharacterListResponse>> getAllCharacter(
        @PathVariable("memberId") Long memberId
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CharacterListResponse characterListResponse;
        if (userDetails == null) {
            characterListResponse = characterService.getAllCharacter(memberId);
        } else {
            Member loginMember = userDetails.getMember();
            characterListResponse = characterService.getAllCharacterByMember(loginMember, memberId);
        }
        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, characterListResponse));
    }

    @Operation(summary = "회원의 특정 캐릭터 상세 조회", description = "회원의 특정 캐릭터를 조회합니다.")
    @Parameter(name = "characterId", description = "조회할 캐릭터의 id", example = "12", required = true)
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
    @GetMapping("/{characterId}")
    public ResponseEntity<DataResponse<CharacterResponse>> getCharacter(
        @PathVariable("characterId") Long characterId
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        CharacterResponse characterResponse = characterService.getCharacterDetail(member, characterId);
        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, characterResponse));
    }

    @Operation(
            summary = "캐릭터 이름 수정",
            description = "캐릭터의 이름을 수정합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "캐릭터 이름",
                    required = true
            )
    )
    @Parameter(name = "characterId", description = "수정할 캐릭터의 id", example = "12", required = true)
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
    @PutMapping("/{characterId}")
    public ResponseEntity<BaseResponse> updateCharacterName(
        @PathVariable("characterId") Long characterId
        , @RequestBody CharacterNameUpdateRequest request
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        characterService.updateCharacterName(member, characterId, request);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(StatusCode.OK));
    }

    @Operation(summary = "캐릭터 삭제", description = "회원의 특정 캐릭터을 삭제합니다.")
    @Parameter(name = "characterId", description = "삭제할 캐릭터의 id", example = "12", required = true)
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
    @DeleteMapping("{characterId}")
    public ResponseEntity<BaseResponse> deleteCharacter(
        @PathVariable("characterId") Long characterId
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        characterService.deleteCharacter(member, characterId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(StatusCode.OK));
    }
}
