package com.nexters.kekechebe.domain.memo.repository;

import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    Optional<Memo> findByIdAndMember(Long memoId, Member member);
}
