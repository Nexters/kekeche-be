package com.nexters.kekechebe.domain.character.service;

import com.nexters.kekechebe.domain.character.dto.request.CharacterCreateRequest;
import com.nexters.kekechebe.domain.character.dto.request.CharacterNameUpdateRequest;
import com.nexters.kekechebe.domain.character.dto.request.SpecialtyCreateRequest;
import com.nexters.kekechebe.domain.character.dto.request.SpecialtyInfo;
import com.nexters.kekechebe.domain.character.dto.response.*;
import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.character.enums.CharacterAsset;
import com.nexters.kekechebe.domain.character.repository.CharacterRepository;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.member.repository.MemberRepository;
import com.nexters.kekechebe.domain.memo.repository.MemoRepository;
import com.nexters.kekechebe.domain.specialty.entity.Specialty;
import com.nexters.kekechebe.domain.specialty.repository.SpecialtyRepository;
import com.nexters.kekechebe.exceptions.CustomException;
import com.nexters.kekechebe.util.time.TimeUtil;
import com.nexters.kekechebe.util.time.Today;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nexters.kekechebe.domain.character.enums.Level.LEVEL1;
import static com.nexters.kekechebe.exceptions.StatusCode.UNAUTHORIZED_REQUEST;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private static final int CHARACTER_LIMIT = 6;
    private static final int MEMO_LIMIT = 3;
    private static final int SPECIALTY_LIMIT = 4;

    private final CharacterRepository characterRepository;
    private final MemberRepository memberRepository;
    private final SpecialtyRepository specialtyRepository;
    private final MemoRepository memoRepository;

    @Transactional
    public CharacterIdResponse saveCharacter(Member member, CharacterCreateRequest request) {
        CharacterAsset.validateCharacterAsset(request);
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
            .map(character -> new CharacterResponse(character, isMemoValid(accessMember, character)))
            .toList();

        return CharacterListResponse.builder()
            .characters(characterListDto)
            .isMe(isMe)
            .memberNickname(accessMember.getNickname())
            .cheerCount(accessMember.getCheerCount())
            .build();
    }

    @Transactional(readOnly = true)
    public List<CharacterThumbnailResponse> getAllCharacterThumbnail(Member member) {
        List<Character> characterList = characterRepository.findAllByMemberId(member.getId());
        return characterList
            .stream()
            .map(character -> new CharacterThumbnailResponse(character, isMemoValid(member, character)))
            .toList();
    }

    @Transactional(readOnly = true)
    public CharacterResponse getCharacterDetail(Member member, Long characterId) {
        Character character = characterRepository.findById(characterId)
            .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        validateMember(member, character);

        return new CharacterResponse(character, isMemoValid(member, character));
    }

    @Transactional
    public void updateCharacterName(Member member, Long characterId, CharacterNameUpdateRequest request) {
        Character character = characterRepository.findById(characterId)
            .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        validateMember(member, character);
        character.updateName(request.getName());
    }

    @Transactional
    public void deleteCharacter(Member member, Long characterId) {
        Character character = characterRepository.findById(characterId)
            .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        validateMember(member, character);
        characterRepository.deleteById(character.getId());
    }

    @Transactional
    public SpecialtyResponse saveSpecialty(Member member, Long characterId, SpecialtyCreateRequest request) {
        List<SpecialtyInfo> requestSpecialties = request.getSpecialties();

        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        validateMember(member, character);
        validateSpecialtyLimit(character, requestSpecialties.size());

        List<Specialty> specialties = requestSpecialties.stream()
                .map(specialtyInfo -> Specialty.builder()
                        .content(specialtyInfo.getContent())
                        .memoCnt(0)
                        .character(character)
                        .build())
                .toList();
        List<Specialty> savedSpecialties = specialtyRepository.saveAll(specialties);

        List<SpecialtyDetail> specialtyDetails = savedSpecialties.stream()
                .map(Specialty::toSpecialtyDetail)
                .toList();

        return SpecialtyResponse.builder()
                .specialties(specialtyDetails)
                .build();
    }

    @Transactional
    public void deleteSpecialty(Member member, Long characterId, Long specialtyId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new NoResultException("주특기를 찾을 수 없습니다."));

        validateMember(member, character);

        specialtyRepository.delete(specialty);
    }

    public SpecialtyResponse getSpecialty(Member member, Long characterId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        validateMember(member, character);

        List<Specialty> specialties = specialtyRepository.findAllByCharacter(character);
        List<SpecialtyDetail> specialtyDetails = specialties.stream()
                .map(Specialty::toSpecialtyDetail)
                .toList();

        return SpecialtyResponse.builder()
                .specialties(specialtyDetails)
                .build();
    }

    private void validateMember(Member member, Character character) {
        if (!member.getId().equals(character.getMember().getId())) {
            throw new CustomException(UNAUTHORIZED_REQUEST);
        }
    }

    private void validateSpecialtyLimit(Character character, int requestSpecialtyCnt) {
        int specialtyCnt = specialtyRepository.countByCharacter(character);

        if (specialtyCnt + requestSpecialtyCnt > SPECIALTY_LIMIT) {
            throw new IllegalStateException("캐릭터당 허용된 기록 개수를 초과하였습니다.");
        }
    }

    private boolean isMemoValid(Member member, Character character) {
        Today today = TimeUtil.getStartAndEndOfToday();

        int memoCnt = memoRepository.countByMemberAndCharacterAndCreatedAtBetween(member, character, today.getStartOfDay(), today.getEndOfDay());

        return memoCnt < MEMO_LIMIT;
    }
}
