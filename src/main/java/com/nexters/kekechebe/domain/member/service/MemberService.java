package com.nexters.kekechebe.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexters.kekechebe.domain.character.repository.CharacterRepository;
import com.nexters.kekechebe.domain.member.dto.response.MemberResponse;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.memo.repository.MemoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final CharacterRepository characterRepository;
    private final MemoRepository memoRepository;

    @Transactional(readOnly = true)
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
}