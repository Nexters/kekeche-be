package com.nexters.kekechebe.domain.character.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CharacterListResponse {
    private List<CharacterResponse> characters;
    private Boolean isMe;
    private String memberNickname;
    private Integer cheerCount;
    private Long memoCount;
    private Long joinDays;
}
