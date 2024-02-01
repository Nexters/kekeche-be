package com.nexters.kekechebe.domain.character.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CharacterIdResponse {
    private final Long id;
}
