package com.nexters.kekechebe.domain.memo.dto.request;

import lombok.Getter;

@Getter
public class MemoCreateRequest {
    private long characterId;
    private String content;
}
