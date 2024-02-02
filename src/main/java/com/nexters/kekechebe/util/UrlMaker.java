package com.nexters.kekechebe.util;

import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.domain.character.enums.Item;

public class UrlMaker {
    private final static String BASE_URL = "https://kr.object.ncloudstorage.com/kekeche-character";

    public static String madeCharacterUrl(Character character) {
        String prefix = "character";
        String shape = character.getShape().getValue().toString();
        String variation = character.getVariation().getIndex().toString();
        String color = character.getColor().getValue().toString();
        String png = ".png";
        return String.format("%s/%s/%s/%s/%s%s", BASE_URL, prefix, shape, variation, color, png);
    }

    public static String madeItemUrl(Character character) {
        int itemValue = character.getItem().getValue();
        if (itemValue == Item.BLANK.getValue()) {
            return "";
        }
        String prefix = "item";
        String item = String.valueOf(itemValue);
        String png = ".png";
        return String.format("%s/%s/%s%s", BASE_URL, prefix, item, png);
    }
}
