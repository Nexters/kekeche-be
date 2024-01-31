package com.nexters.kekechebe.domain.character.controller;

import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<BaseResponse> saveCharacter(
        @RequestBody CharacterCreateRequest request
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        CharacterIdResponse characterIdResponse = characterService.saveCharacter(member, request);
        return ResponseEntity.ok(new DataResponse<>(StatusCode.CREATED, characterIdResponse));
    }

    @GetMapping( "/member/{memberId}")
    public ResponseEntity<BaseResponse> getAllCharacterByMember(
        @PathVariable("memberId") Long memberId
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member loginMember = userDetails.getMember();
        CharacterListResponse characterListResponse = characterService.getAllCharacterByMember(loginMember, memberId);
        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, characterListResponse));
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<BaseResponse> getCharacter(
        @PathVariable("characterId") Long characterId
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        CharacterResponse characterResponse = characterService.getCharacterDetail(member, characterId);
        return ResponseEntity.ok(new DataResponse<>(StatusCode.OK, characterResponse));
    }

    @PutMapping("/{characterId}")
    public ResponseEntity<BaseResponse> updateCharacterName(
        @PathVariable("characterId") Long characterId
        , @RequestBody CharacterNameUpdateRequest request
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        characterService.updateCharacterName(member, characterId, request);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(StatusCode.OK));
    }

    @DeleteMapping("{characterId}")
    public ResponseEntity<BaseResponse> deleteCharacter(
        @PathVariable("characterId") Long characterId
        , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        characterService.deleteCharacter(member, characterId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(StatusCode.OK));
    }
}
