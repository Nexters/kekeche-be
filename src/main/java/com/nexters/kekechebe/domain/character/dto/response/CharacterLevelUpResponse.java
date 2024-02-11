package com.nexters.kekechebe.domain.character.dto.response;

import static com.nexters.kekechebe.util.UrlMaker.*;

import java.lang.reflect.Type;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexters.kekechebe.domain.character.entity.Character;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CharacterLevelUpResponse {
    private Long id;
    private String name;
    private Integer level;
    private Boolean isLevelUp;
    private String characterImage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String itemImage;
    private List<Integer> keywords;

    public static CharacterLevelUpResponse from(Character character, boolean isLevelUp) {
        return CharacterLevelUpResponse.builder()
            .id(character.getId())
            .name(character.getName())
            .level(character.getLevel())
            .isLevelUp(isLevelUp)
            .characterImage(madeCharacterUrl(character))
            .itemImage(madeItemUrl(character))
            .keywords(parseKeywords(character.getKeywords()))
            .build();
    }

    private static List<Integer> parseKeywords(String keywords) {
        Gson gson = new Gson();

        Type listType = new TypeToken<List<Integer>>() {}.getType();

        return gson.fromJson(keywords, listType);
    }
}
