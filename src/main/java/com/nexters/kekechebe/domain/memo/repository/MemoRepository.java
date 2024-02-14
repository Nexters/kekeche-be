package com.nexters.kekechebe.domain.memo.repository;

import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.memo.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    @Query("SELECT m FROM Memo m JOIN FETCH m.character LEFT JOIN FETCH m.memoSpecialties WHERE m.id = :memoId AND m.member = :member")
    Optional<Memo> findByIdAndMember(@Param("memoId") Long memoId, @Param("member") Member member);

    @Query("SELECT m FROM Memo m JOIN FETCH m.character WHERE m.member = :member")
    Page<Memo> findAllByMember(@Param("member") Member member, Pageable pageable);

    Page<Memo> findAllByMemberAndCharacter(Member member, Character character, Pageable pageable);

    int countByMemberAndCharacterAndCreatedAtBetween(Member member, Character character, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("SELECT m FROM Memo m JOIN FETCH m.character WHERE m.member = :member and m.content LIKE %:keyword%")
    Page<Memo> searchAllByKeyword(@Param("member") Member member, @Param("keyword") String keyword, Pageable pageable);

    long countMemoByMember(Member member);
}
