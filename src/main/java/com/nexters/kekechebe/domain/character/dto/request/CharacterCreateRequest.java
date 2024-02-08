package com.nexters.kekechebe.domain.character.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class CharacterCreateRequest {
    private String name;
    private Integer shapeIdx;
    private Integer colorIdx;
    private List<Integer> keywords;
    private Integer itemIdx;


}
