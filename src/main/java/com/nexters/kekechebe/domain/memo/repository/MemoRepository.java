package com.nexters.kekechebe.domain.memo.repository;

import com.nexters.kekechebe.domain.memo.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
