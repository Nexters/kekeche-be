package com.nexters.kekechebe.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.kekechebe.domain.character.repository.CharacterRepository;
import com.nexters.kekechebe.domain.member.dto.response.MemberCheerResponse;
import com.nexters.kekechebe.domain.member.dto.response.MemberResponse;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.member.repository.MemberRepository;
import com.nexters.kekechebe.domain.memo.repository.MemoRepository;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final CharacterRepository characterRepository;
    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;

    public MemberResponse getMemberInfo(Member member) {
        long characterCount = characterRepository.countCharacterByMember(member);
        long memoCount = memoRepository.countMemoByMember(member);

        return MemberResponse.builder()
            .memberId(member.getId())
            .nickname(member.getNickname())
            .characterCount(characterCount)
            .memoCount(memoCount)
            .build();
    }

    @Transactional
    public MemberCheerResponse updateCheerCount(Long accessMemberId) {
        Member accessMember = memberRepository.findById(accessMemberId)
            .orElseThrow(() -> new NoResultException("회원을 찾을 수 없습니다."));
        Integer nextCount = accessMember.updateCheerCount(1);
        return MemberCheerResponse.builder()
            .memberId(accessMember.getId())
            .cheerCount(nextCount)
            .build();
    }
}