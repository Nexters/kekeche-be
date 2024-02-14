package com.nexters.kekechebe.domain.character.dto.response;

import static com.nexters.kekechebe.util.UrlMaker.*;

import com.nexters.kekechebe.domain.character.entity.Character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CharacterThumbnailResponse {
    private Long id;
    private String name;
    private String characterImage;

    private Boolean isMemoValid;

    public CharacterThumbnailResponse(Character character, Boolean isMemoValid) {
        this.id = character.getId();
        this.name = character.getName();
        this.characterImage = madeCharacterThumbnailUrl(character);
        this.isMemoValid = isMemoValid;
    }
}
