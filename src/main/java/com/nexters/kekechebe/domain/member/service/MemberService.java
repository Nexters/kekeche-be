package com.nexters.kekechebe.domain.member.service;

import org.springframework.stereotype.Service;

import com.nexters.kekechebe.domain.character.repository.CharacterRepository;
import com.nexters.kekechebe.domain.member.dto.response.MemberResponse;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.member.repository.MemberRepository;
import com.nexters.kekechebe.domain.memo.repository.MemoRepository;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CharacterRepository characterRepository;
    private final MemoRepository memoRepository;

    public MemberResponse getMemberInfo(long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NoResultException("회원을 찾을 수 없습니다."));
        long characterCount = characterRepository.countCharacterByMember(member);
        long memoCount = memoRepository.countMemoByMember(member);

        return MemberResponse.builder()
            .memberId(member.getId())
            .characterCount(characterCount)
            .memoCount(memoCount)
            .build();
    }
}