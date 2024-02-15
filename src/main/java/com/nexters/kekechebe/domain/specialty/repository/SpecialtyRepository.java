package com.nexters.kekechebe.domain.specialty.repository;

import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.specialty.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    Optional<Specialty> findByIdAndCharacter(Long specialtyId, Character character);

    List<Specialty> findAllByCharacter(Character character);

    int countByCharacterAndCreatedAtBetween(Character character, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
