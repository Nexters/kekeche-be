package com.nexters.kekechebe.domain.hashtag.repository;

import com.nexters.kekechebe.domain.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByContent(String content);
}
