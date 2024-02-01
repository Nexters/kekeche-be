package com.nexters.kekechebe.domain.character.service;

import static com.nexters.kekechebe.exceptions.StatusCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.kekechebe.domain.character.dto.request.CharacterCreateRequest;
import com.nexters.kekechebe.domain.character.dto.request.CharacterNameUpdateRequest;
import com.nexters.kekechebe.domain.character.dto.response.CharacterIdResponse;
import com.nexters.kekechebe.domain.character.dto.response.CharacterListResponse;
import com.nexters.kekechebe.domain.character.dto.response.CharacterResponse;
import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.character.enums.CharacterAsset;
import com.nexters.kekechebe.domain.character.enums.Keyword;
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
        validateCharacterLimit(member);

        String keywordParsing = convertKeywordListToString(request.getKeywords());

        Character character = Character.builder()
            .name(request.getName())
            .level(1)
            .exp(0)
            .variation(CharacterAsset.Variation.VAR1)
            .shape(request.getShape())
            .color(request.getColor())
            .item(request.getItem())
            .keywords(keywordParsing)
            .member(member)
            .build();

        Character saveCharacter = characterRepository.save(character);

        return CharacterIdResponse.builder()
            .id(saveCharacter.getId())
            .build();
    }

    private void validateCharacterLimit(Member member) {
        int characterCount = characterRepository.countCharacterByMember(member);
        if (characterCount >= CHARACTER_LIMIT) {
            throw new IllegalStateException("허용된 캐릭터 개수를 초과하였습니다.");
        }
    }

    private String convertKeywordListToString(List<Keyword> keywordList) {
        return keywordList.stream()
            .map(Keyword::getIndex)
            .map(String::valueOf)
            .collect(Collectors.joining(",", "[", "]"));
    }

    @Transactional(readOnly = true)
    public CharacterListResponse getAllCharacterByMember(Member loginMember, Long accessMemberId) {
        Member accessMember = memberRepository.findById(accessMemberId)
            .orElseThrow(() -> new NoResultException("회원을 찾을 수 없습니다."));

        Boolean isMe = loginMember.getId().equals(accessMember.getId());

        List<Character> characterList = characterRepository.findAllByMemberId(accessMemberId);

        List<CharacterResponse> characterListDto = characterList
            .stream()
            .map(CharacterResponse::new)
            .toList();

        return CharacterListResponse.builder()
            .characters(characterListDto)
            .isMe(isMe)
            .build();
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
