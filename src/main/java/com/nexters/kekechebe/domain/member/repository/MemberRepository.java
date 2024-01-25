package com.nexters.kekechebe.domain.member.repository;

import java.util.Optional;

import com.nexters.kekechebe.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByKakaoId(Long kakaoId);
}
