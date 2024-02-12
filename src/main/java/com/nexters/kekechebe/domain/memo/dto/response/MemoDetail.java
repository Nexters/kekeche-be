package com.nexters.kekechebe.domain.memo.dto.response;

import com.nexters.kekechebe.domain.character.dto.response.SpecialtyDetail;
import com.nexters.kekechebe.domain.memo.dto.CharacterDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MemoDetail {
    private long id;

    private CharacterDetail character;

    private String content;

    private List<SpecialtyDetail> specialties;

    private boolean isModified;

    private LocalDateTime createdAt;
}
