package com.nexters.kekechebe.domain.character.dto.response;

import static com.nexters.kekechebe.domain.character.enums.Level.*;
import static com.nexters.kekechebe.util.UrlMaker.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexters.kekechebe.domain.character.entity.Character;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CharacterResponse {
    private Long id;
    private String name;
    private Integer level;
    private Integer totalExp;
    private Integer currentExp;
    private Integer nextExp;
    private String characterImage;
    private String itemImage;
    private List<Integer> keywords;

    public CharacterResponse(Character character) {
        this.id = character.getId();
        this.name = character.getName();
        this.level = character.getLevel();
        this.totalExp = character.getExp();
        this.currentExp = getCurrentExpThreshold(character.getExp());
        this.nextExp = getNextExpThreshold(character.getExp());
        this.characterImage = madeCharacterUrl(character);
        this.itemImage = madeItemUrl(character);
        this.keywords = parseKeywords(character.getKeywords());
    }

    private List<Integer> parseKeywords(String keywords) {
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Integer>>() {}.getType();

        return gson.fromJson(keywords, listType);
    }
}
