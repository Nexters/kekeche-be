package com.nexters.kekechebe.domain.memo_specialty.repository;

import com.nexters.kekechebe.domain.memo_specialty.entity.MemoSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoSpecialtyRepository extends JpaRepository<MemoSpecialty, Long> {
    void deleteAllBySpecialtyIdIn(List<Long> specialtyIds);
}
