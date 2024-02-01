package com.nexters.kekechebe.domain.character.dto.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexters.kekechebe.domain.character.entity.Character;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.List;

import static com.nexters.kekechebe.domain.character.enums.Level.getNextLevelThreshold;

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
    private List<Integer> keywords;

    public CharacterResponse(Character character) {
        this.id = character.getId();
        this.name = character.getName();
        this.level = character.getLevel();
        this.exp = character.getExp();
        this.nextLevel = getNextLevelThreshold(character.getExp());
        this.characterImage = "url";
        this.itemImage = "url";
        this.keywords = parseKeywords(character.getKeywords());
    }

    private List<Integer> parseKeywords(String keywords) {
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Integer>>() {}.getType();

        return gson.fromJson(keywords, listType);
    }
}
