package com.nexters.kekechebe.util;

import static com.nexters.kekechebe.domain.character.enums.CharacterAsset.*;

import com.nexters.kekechebe.domain.character.entity.Character;

public class UrlMaker {
    private final static String BASE_URL = "https://kr.object.ncloudstorage.com/kekeche-character";

    public static String madeCharacterUrl(Character character) {
        return madeUrl(character, "character");
    }

    public static String madeCharacterThumbnailUrl(Character character) {
        return madeUrl(character, "character_thumbnail");
    }

    private static String madeUrl(Character character, String prefix) {
        // String prefix = "character";
        String shape = String.valueOf(character.getShapeIdx());
        String variation = character.getVariation().getIndex().toString();
        String color = String.valueOf(character.getColorIdx());
        String png = ".webp";
        return String.format("%s/%s/%s/%s/%s%s", BASE_URL, prefix, shape, variation, color, png);
    }

    public static String madeItemUrl(Character character) {
        Integer itemValue = character.getItemIdx();
        if (itemValue == NO_ITEM) {
            return null;
        }
        String prefix = "item";
        String item = String.valueOf(itemValue);
        String png = ".webp";
        return String.format("%s/%s/%s%s", BASE_URL, prefix, item, png);
    }
}
