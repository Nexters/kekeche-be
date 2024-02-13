package com.nexters.kekechebe.domain.memo.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CharacterDetail {
    private long id;
    private String name;
    private String characterImage;
}
