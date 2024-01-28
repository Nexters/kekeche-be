package com.nexters.kekechebe.domain.character.repository;

import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    long countCharacterByMember(Member member);
}
