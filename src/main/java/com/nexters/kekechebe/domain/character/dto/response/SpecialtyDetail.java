package com.nexters.kekechebe.domain.character.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpecialtyDetail {
    private long id;

    private String content;

    private int memoCnt;
}
