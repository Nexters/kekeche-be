package com.nexters.kekechebe.domain.memo.repository;

import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.memo.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    @Query("SELECT m FROM Memo m JOIN FETCH m.character JOIN FETCH m.memoSpecialties WHERE m.id = :memoId AND m.member = :member")
    Optional<Memo> findByIdAndMember(Long memoId, Member member);

    @Query("SELECT m FROM Memo m JOIN FETCH m.character WHERE m.member = :member")
    Page<Memo> findAllByMember(Member member, Pageable pageable);

    Page<Memo> findAllByMemberAndCharacter(Member member, Character character, Pageable pageable);

    int countByMemberAndCharacterAndCreatedAtBetween(Member member, Character character, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("SELECT m FROM Memo m JOIN FETCH m.character WHERE m.member = :member and m.content LIKE %:keyword%")
    Page<Memo> searchAllByKeyword(Member member, String keyword, Pageable pageable);

    long countMemoByMember(Member member);
}
