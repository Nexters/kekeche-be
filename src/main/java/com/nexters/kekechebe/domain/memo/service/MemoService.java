package com.nexters.kekechebe.domain.memo.service;

import com.nexters.kekechebe.domain.character.dto.response.CharacterLevelUpResponse;
import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.character.repository.CharacterRepository;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.memo.dto.request.MemoCreateRequest;
import com.nexters.kekechebe.domain.memo.dto.request.MemoUpdateRequest;
import com.nexters.kekechebe.domain.memo.dto.response.MemoPage;
import com.nexters.kekechebe.domain.memo.entity.Memo;
import com.nexters.kekechebe.domain.memo.repository.MemoRepository;
import com.nexters.kekechebe.domain.memo_specialty.entity.MemoSpecialty;
import com.nexters.kekechebe.domain.memo_specialty.repository.MemoSpecialtyRepository;
import com.nexters.kekechebe.domain.specialty.entity.Specialty;
import com.nexters.kekechebe.domain.specialty.repository.SpecialtyRepository;
import com.nexters.kekechebe.util.specialty.SpecialtyUtil;
import com.nexters.kekechebe.util.time.TimeUtil;
import com.nexters.kekechebe.util.time.Today;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoService {
    private static final int MEMO_LIMIT = 3;

    private final CharacterHelperService characterHelperService;
    private final MemoRepository memoRepository;
    private final SpecialtyRepository specialtyRepository;
    private final MemoSpecialtyRepository memoSpecialtyRepository;
    private final CharacterRepository characterRepository;

    @Transactional
    public CharacterLevelUpResponse saveMemo(Member member, MemoCreateRequest request) {
        long characterId = request.getCharacterId();
        String content = request.getContent();
        List<Long> specialtyIds = request.getSpecialtyIds();

        Character character = characterRepository.findByIdAndMember(characterId, member)
                .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        validateMemoLimit(member, character);

        Memo memo = Memo.builder()
                .content(content)
                .member(member)
                .character(character)
                .build();
        memoRepository.save(memo);

        List<Specialty> foundSpecialties = specialtyIds.stream()
                .map(specialtyId -> specialtyRepository.findByIdAndCharacter(specialtyId, character)
                        .orElseThrow(() -> new NoResultException("해당 캐릭터에 해당하는 주특기를 찾을 수 없습니다.")))
                .toList();

        foundSpecialties.forEach(Specialty::apply);

        List<MemoSpecialty> memoSpecialties = foundSpecialties.stream()
                .map(specialty -> MemoSpecialty.builder()
                        .memo(memo)
                        .specialty(specialty)
                        .build())
                .toList();
        memoSpecialtyRepository.saveAll(memoSpecialties);

        characterHelperService.updateExp(character);
        boolean isLevelUp = characterHelperService.isLevelUp(character);

        return CharacterLevelUpResponse.from(character, isLevelUp);
    }

    public MemoPage getAllMemos(Member member, Pageable pageable) {
        Page<Memo> memos = memoRepository.findAllByMember(member, pageable);

        return MemoPage.from(memos);
    }

    public MemoPage getCharacterMemos(Member member, long characterId, Pageable pageable) {
        Character character = characterRepository.findByIdAndMember(characterId, member)
                .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        Page<Memo> memos = memoRepository.findAllByMemberAndCharacter(member, character, pageable);

        return MemoPage.from(memos);
    }

    @Transactional
    public void updateMemo(Member member, long memoId, MemoUpdateRequest request) {
        List<Long> afterSpecialtyIds = request.getSpecialtyIds();
        String content = request.getContent();

        Memo memo = memoRepository.findByIdAndMember(memoId, member)
                .orElseThrow(() -> new NoResultException("기록을 찾을 수 없습니다."));
        Character character = memo.getCharacter();

        List<MemoSpecialty> beforeMemoSpecialties = memo.getMemoSpecialties();
        List<Long> beforeSpecialtyIds = beforeMemoSpecialties.stream()
                .map(MemoSpecialty::getSpecialty)
                .map(Specialty::getId)
                .toList();

        List<Long> idsToDeleteSpecialties = SpecialtyUtil.getIdsToDeleteSpecialties(beforeSpecialtyIds, afterSpecialtyIds);
        List<Long> idsToCreateSpecialties = SpecialtyUtil.getIdsToCreateSpecialties(beforeSpecialtyIds, afterSpecialtyIds);

        List<Specialty> foundDeleteSpecialties = specialtyRepository.findAllById(idsToDeleteSpecialties);
        foundDeleteSpecialties.forEach(Specialty::remove);

        memoSpecialtyRepository.deleteAllBySpecialtyIdIn(idsToDeleteSpecialties);

        List<Specialty> foundSpecialties = idsToCreateSpecialties.stream()
                .map(specialtyId -> specialtyRepository.findByIdAndCharacter(specialtyId, character)
                        .orElseThrow(() -> new NoResultException("해당 캐릭터에 해당하는 주특기를 찾을 수 없습니다.")))
                .toList();
        foundSpecialties.forEach(Specialty::apply);

        List<MemoSpecialty> memoSpecialties = foundSpecialties.stream()
                .map(specialty -> MemoSpecialty.builder()
                        .memo(memo)
                        .specialty(specialty)
                        .build())
                .toList();
        memoSpecialtyRepository.saveAll(memoSpecialties);

        memo.updateContent(content);
    }

    @Transactional
    public void deleteMemo(Member member, long memoId) {
        Memo memo = memoRepository.findByIdAndMember(memoId, member)
                .orElseThrow(() -> new NoResultException("기록을 찾을 수 없습니다."));

        memoRepository.delete(memo);
    }

    public MemoPage search(Member member, String keyword, Pageable pageable) {
        Page<Memo> memos = memoRepository.searchAllByKeyword(member, keyword, pageable);

        return MemoPage.from(memos);
    }

    private void validateMemoLimit(Member member, Character character) {
        Today today = TimeUtil.getStartAndEndOfToday();

        int memoCnt = memoRepository.countByMemberAndCharacterAndCreatedAtBetween(member, character, today.getStartOfDay(), today.getEndOfDay());

        if (memoCnt >= MEMO_LIMIT) {
            throw new IllegalStateException("캐릭터당 허용된 기록 개수를 초과하였습니다.");
        }
    }
}
