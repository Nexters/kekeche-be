package com.nexters.kekechebe.domain.character.dto.response;

import static com.nexters.kekechebe.domain.character.enums.Level.*;

import com.nexters.kekechebe.domain.character.entity.Character;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CharacterResponse {
    private Long id;
    private String name;
    private Integer level;
    private Integer exp;
    private Integer nextLevel;
    private String characterImage;
    private String itemImage;
    private String keyword;

    public CharacterResponse(Character character) {
        this.id = character.getId();
        this.name = character.getName();
        this.level = character.getLevel();
        this.exp = character.getExp();
        this.nextLevel = getNextLevelThreshold(character.getExp());
        this.characterImage = "url";
        this.itemImage = "url";
        this.keyword = character.getKeyword();
    }


}
