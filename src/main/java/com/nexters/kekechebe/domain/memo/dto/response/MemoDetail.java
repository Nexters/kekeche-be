package com.nexters.kekechebe.domain.memo.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemoDetail {
    private long id;

    private long characterId;

    private String content;
}
