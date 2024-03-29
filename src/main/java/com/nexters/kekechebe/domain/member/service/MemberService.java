package com.nexters.kekechebe.domain.member.service;

import com.nexters.kekechebe.domain.character.repository.CharacterRepository;
import com.nexters.kekechebe.domain.member.dto.response.MemberCheerResponse;
import com.nexters.kekechebe.domain.member.dto.response.MemberInfoResponse;
import com.nexters.kekechebe.domain.member.dto.response.MemberResponse;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.member.repository.MemberRepository;
import com.nexters.kekechebe.domain.memo.repository.MemoRepository;
import com.nexters.kekechebe.exceptions.CustomException;
import com.nexters.kekechebe.util.time.TimeUtil;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.nexters.kekechebe.exceptions.StatusCode.UNAUTHORIZED_REQUEST;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final CharacterRepository characterRepository;
    private final MemoRepository memoRepository;
    private final MemberRepository memberRepository;

    public MemberResponse getMember(Member member) {
        return MemberResponse.builder()
            .memberId(member.getId())
            .nickname(member.getNickname())
            .build();
    }

    public MemberInfoResponse getMemberInfo(Member member) {
        long characterCount = characterRepository.countCharacterByMember(member);
        long memoCount = memoRepository.countMemoByMember(member);

        return MemberInfoResponse.builder()
            .memberId(member.getId())
            .nickname(member.getNickname())
            .characterCount(characterCount)
            .memoCount(memoCount)
            .joinDays(getJoinDays(member.getCreatedAt()))
            .build();
    }

    @Transactional
    public MemberCheerResponse updateCheerCount(Member loginMember, Long accessMemberId) {
        if (loginMember != null && accessMemberId.equals(loginMember.getId())) {
            throw new CustomException(UNAUTHORIZED_REQUEST);
        }
        Member accessMember = memberRepository.findById(accessMemberId)
            .orElseThrow(() -> new NoResultException("회원을 찾을 수 없습니다."));
        Integer nextCount = accessMember.updateCheerCount(1);
        return MemberCheerResponse.builder()
            .memberId(accessMember.getId())
            .cheerCount(nextCount)
            .build();
    }

    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }

    private long getJoinDays(LocalDateTime createdAt) {
        return TimeUtil.getDateBetween(createdAt.toLocalDate(), LocalDate.now()) + 1;
    }
}