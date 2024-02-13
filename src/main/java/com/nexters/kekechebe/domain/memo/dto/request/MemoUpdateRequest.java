package com.nexters.kekechebe.domain.memo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class MemoUpdateRequest {
    @NotBlank
    private String content;

    private List<Long> specialtyIds;
}
