package com.nexters.kekechebe.domain.character.dto.response;

import static com.nexters.kekechebe.util.UrlMaker.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.util.character.LevelUtil;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String itemImage;
    private List<Integer> keywords;

    public CharacterResponse(Character character) {
        this.id = character.getId();
        this.name = character.getName();
        this.level = character.getLevel();
        this.totalExp = character.getExp();
        this.currentExp = getLevelInfo(character.getExp()).getCurrentExpThreshold();
        this.nextExp = getLevelInfo(character.getExp()).getNextExpThreshold();
        this.characterImage = madeCharacterUrl(character);
        this.itemImage = madeItemUrl(character);
        this.keywords = parseKeywords(character.getKeywords());
    }

    private List<Integer> parseKeywords(String keywords) {
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Integer>>() {}.getType();

        return gson.fromJson(keywords, listType);
    }

    private LevelUtil.LevelInfo getLevelInfo(Integer exp) {
        return LevelUtil.getLevelInfo(exp);
    }
}
