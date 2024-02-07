package com.nexters.kekechebe.domain.character.service;

import static com.nexters.kekechebe.domain.character.enums.CharacterAsset.*;
import static com.nexters.kekechebe.domain.character.enums.Keyword.*;
import static com.nexters.kekechebe.domain.character.enums.Level.*;
import static com.nexters.kekechebe.exceptions.StatusCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.kekechebe.domain.character.dto.request.CharacterCreateRequest;
import com.nexters.kekechebe.domain.character.dto.request.CharacterNameUpdateRequest;
import com.nexters.kekechebe.domain.character.dto.response.CharacterThumbnailResponse;
import com.nexters.kekechebe.domain.character.dto.response.CharacterIdResponse;
import com.nexters.kekechebe.domain.character.dto.response.CharacterListResponse;
import com.nexters.kekechebe.domain.character.dto.response.CharacterResponse;
import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.character.enums.CharacterAsset;
import com.nexters.kekechebe.domain.character.repository.CharacterRepository;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.member.repository.MemberRepository;
import com.nexters.kekechebe.exceptions.CustomException;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private static final int CHARACTER_LIMIT = 6;

    private final CharacterRepository characterRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CharacterIdResponse saveCharacter(Member member, CharacterCreateRequest request) {
        validateCharacterAsset(request);
        validateCharacterLimit(member);

        Character character = Character.builder()
            .name(request.getName())
            .level(LEVEL1.getLevel())
            .exp(0)
            .variation(CharacterAsset.Variation.VAR1)
            .shapeIdx(request.getShapeIdx())
            .colorIdx(request.getColorIdx())
            .itemIdx(request.getItemIdx())
            .keywords(request.getKeywords().toString())
            .member(member)
            .build();

        Character saveCharacter = characterRepository.save(character);

        return CharacterIdResponse.builder()
            .id(saveCharacter.getId())
            .build();
    }

    private void validateCharacterAsset(CharacterCreateRequest request) {
        if (!isShapeValid(request.getShapeIdx())) {
            throw new IllegalArgumentException("잘못된 캐릭터 에셋 입니다.(body)");
        }
        if (!isColorValid(request.getColorIdx())) {
            throw new IllegalArgumentException("잘못된 캐릭터 에셋 입니다.(color)");
        }
        if (!isItemValid(request.getItemIdx())) {
            throw new IllegalArgumentException("잘못된 캐릭터 에셋 입니다.(item)");
        }
        if (!areKeywordsValid(request.getKeywords())) {
            throw new IllegalArgumentException("잘못된 키워드 입니다.");
        }
    }

    private void validateCharacterLimit(Member member) {
        int characterCount = characterRepository.countCharacterByMember(member);
        if (characterCount >= CHARACTER_LIMIT) {
            throw new IllegalStateException("허용된 캐릭터 개수를 초과하였습니다.");
        }
    }

    @Transactional(readOnly = true)
    public CharacterListResponse getAllCharacterByMember(Member loginMember, Long accessMemberId) {
        Member accessMember = memberRepository.findById(accessMemberId)
            .orElseThrow(() -> new NoResultException("회원을 찾을 수 없습니다."));
        Boolean isMe = loginMember.getId().equals(accessMember.getId());
        return buildCharacterListResponse(accessMember, isMe);
    }

    @Transactional(readOnly = true)
    public CharacterListResponse getAllCharacter(Long accessMemberId) {
        Member accessMember = memberRepository.findById(accessMemberId).orElseThrow(() -> new NoResultException("회원을 찾을 수 없습니다."));
        Boolean isMe = false;
        return buildCharacterListResponse(accessMember, isMe);
    }

    private CharacterListResponse buildCharacterListResponse(Member accessMember, Boolean isMe) {
        List<Character> characterList = characterRepository.findAllByMemberId(accessMember.getId());

        List<CharacterResponse> characterListDto = characterList
            .stream()
            .map(CharacterResponse::new)
            .toList();

        return CharacterListResponse.builder()
            .characters(characterListDto)
            .isMe(isMe)
            .memberNickname(accessMember.getNickname())
            .build();
    }

    @Transactional(readOnly = true)
    public List<CharacterThumbnailResponse> getAllCharacterThumbnail(Member member) {
        List<Character> characterList = characterRepository.findAllByMemberId(member.getId());
        return characterList
            .stream()
            .map(CharacterThumbnailResponse::new)
            .toList();
    }

    @Transactional(readOnly = true)
    public CharacterResponse getCharacterDetail(Member member, Long characterId) {
        Character character = characterRepository.findById(characterId)
            .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        if (!member.getId().equals(character.getMember().getId())) {
            throw new CustomException(TOKEN_UNAUTHORIZED);
        }

        return new CharacterResponse(character);
    }

    @Transactional
    public void updateCharacterName(Member member, Long characterId, CharacterNameUpdateRequest request) {
        Character character = characterRepository.findById(characterId)
            .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        if (!member.getId().equals(character.getMember().getId())) {
            throw new CustomException(TOKEN_UNAUTHORIZED);
        }
        character.updateName(request.getName());
    }

    @Transactional
    public void deleteCharacter(Member member, Long characterId) {
        Character character = characterRepository.findById(characterId)
            .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        if (!member.getId().equals(character.getMember().getId())) {
            throw new CustomException(TOKEN_UNAUTHORIZED);
        }
        characterRepository.deleteById(character.getId());
    }
}
