package com.nexters.kekechebe.domain.specialty.repository;

import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.specialty.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    Optional<Specialty> findByIdAndCharacter(Long specialtyId, Character character);
}
