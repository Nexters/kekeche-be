package com.nexters.kekechebe.domain.member.repository;

import com.nexters.kekechebe.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
