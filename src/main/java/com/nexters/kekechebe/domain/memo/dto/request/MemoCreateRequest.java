package com.nexters.kekechebe.domain.memo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class MemoCreateRequest {
    @NotNull
    private long characterId;

    @NotBlank
    private String content;

    private List<Long> specialtyIds;
}