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
import com.nexters.kekechebe.domain.specialty.entity.Specialty;
import com.nexters.kekechebe.domain.specialty.repository.SpecialtyRepository;
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
    private final CharacterRepository characterRepository;

    @Transactional
    public CharacterLevelUpResponse saveMemo(Member member, MemoCreateRequest request) {
        long characterId = request.getCharacterId();
        String content = request.getContent();
        List<Long> specialtyIds = request.getSpecialtyIds();

        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        validateMemoLimit(member, character);

        Memo memo = Memo.builder()
                .content(content)
                .member(member)
                .character(character)
                .build();
        memoRepository.save(memo);

        List<Specialty> foundSpecialties = specialtyRepository.findAllById(specialtyIds);

        foundSpecialties.forEach(Specialty::apply);

        characterHelperService.updateExp(character);
        boolean isLevelUp = characterHelperService.isLevelUp(character);

        return CharacterLevelUpResponse.from(character, isLevelUp);
    }

    public MemoPage getAllMemos(Member member, Pageable pageable) {
        Page<Memo> memos = memoRepository.findAllByMember(member, pageable);

        return MemoPage.from(memos);
    }

    public MemoPage getCharacterMemos(Member member, long characterId, Pageable pageable) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        Page<Memo> memos = memoRepository.findAllByMemberAndCharacter(member, character, pageable);

        return MemoPage.from(memos);
    }

    @Transactional
    public void updateMemo(Member member, long memoId, MemoUpdateRequest request) {
        List<String> hashtags = request.getHashtags();
        String content = request.getContent();
        String htmlContent = request.getHtmlContent();

        Memo memo = memoRepository.findByIdAndMember(memoId, member)
                .orElseThrow(() -> new NoResultException("기록을 찾을 수 없습니다."));

        hashtagRepository.deleteAll(memo.getHashtags());

        List<Hashtag> buildHashTags = hashtags.stream()
                .map(hashtag -> Hashtag.builder()
                        .content(hashtag)
                        .memo(memo)
                        .build())
                .toList();
        hashtagRepository.saveAll(buildHashTags);

        memo.updateContent(content, htmlContent);
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
