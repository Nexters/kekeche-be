package com.nexters.kekechebe.domain.character.dto.request;

import java.util.List;

import com.nexters.kekechebe.domain.character.enums.CharacterAsset;
import com.nexters.kekechebe.domain.character.enums.Item;
import com.nexters.kekechebe.domain.character.enums.Keyword;

import lombok.Getter;

@Getter
public class CharacterCreateRequest {
    private String name;
    private CharacterAsset.Shape shape;
    private CharacterAsset.Color color;
    private List<Keyword> keywords;
    private Item item;
}
