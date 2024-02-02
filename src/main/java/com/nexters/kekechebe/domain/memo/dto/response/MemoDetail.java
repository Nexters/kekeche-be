package com.nexters.kekechebe.domain.memo.dto.response;

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

    private String htmlContent;

    private String content;

    private List<String> hashtags;

    private boolean isModified;

    private LocalDateTime createdAt;
}
