package com.nexters.kekechebe.domain.memo.service;

import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.character.repository.CharacterRepository;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.member.repository.MemberRepository;
import com.nexters.kekechebe.domain.memo.dto.request.MemoCreateRequest;
import com.nexters.kekechebe.domain.memo.dto.request.MemoUpdateRequest;
import com.nexters.kekechebe.domain.memo.entity.Memo;
import com.nexters.kekechebe.domain.memo.repository.MemoRepository;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;
    private final CharacterRepository characterRepository;

    @Transactional
    public void saveMemo(long memberId, MemoCreateRequest request) {
        long characterId = request.getCharacterId();
        String content = request.getContent();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("회원을 찾을 수 없습니다."));
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new NoResultException("캐릭터를 찾을 수 없습니다."));

        Memo memo = Memo.builder()
                .content(content)
                .member(member)
                .character(character)
                .build();

        memoRepository.save(memo);
    }

    @Transactional
    public void updateMemo(long memberId, long memoId, MemoUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("회원을 찾을 수 없습니다."));
        Memo memo = memoRepository.findByIdAndMember(memoId, member)
                .orElseThrow(() -> new NoResultException("기록을 찾을 수 없습니다."));

        memo.update(request);
    }

    @Transactional
    public void deleteMemo(long memberId, long memoId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoResultException("회원을 찾을 수 없습니다."));
        Memo memo = memoRepository.findByIdAndMember(memoId, member)
                .orElseThrow(() -> new NoResultException("기록을 찾을 수 없습니다."));

        memoRepository.delete(memo);
    }
}
