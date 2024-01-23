package com.nexters.kekechebe.domain.memo.dto.request;

import lombok.Data;

@Data
public class MemoCreateRequest {
    private long characterId;
    private String content;
}
