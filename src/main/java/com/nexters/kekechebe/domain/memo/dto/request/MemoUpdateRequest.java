package com.nexters.kekechebe.domain.memo.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class MemoUpdateRequest {
    private String content;
    private List<String> hashtags;
}
