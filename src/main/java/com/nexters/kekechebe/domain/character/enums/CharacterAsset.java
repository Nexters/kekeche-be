package com.nexters.kekechebe.domain.character.enums;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CharacterAsset {
    private static final List<String> SHAPE_LIST = List.of("Circle", "Square", "Triangle");
    private static final List<String> COLOR_LIST = List.of("Red", "Blue", "Yellow", "Purple", "Green", "Pink");
    private static final List<String> ITEM_LIST = List.of("Laptop", "Exercise", "Money", "Pencil", "Book");

    public static boolean isValidIndex(int index, List<String> list) {
        return index >= 0 && index < list.size();
    }

    public static boolean isShapeValid(int shapeIdx) {
        return isValidIndex(shapeIdx, SHAPE_LIST);
    }

    public static boolean isColorValid(int colorIdx) {
        return isValidIndex(colorIdx, COLOR_LIST);
    }

    public static boolean isItemValid(int itemIdx) {
        return isValidIndex(itemIdx, ITEM_LIST);
    }

    @Getter
    @RequiredArgsConstructor
    public enum Variation {
        VAR1(0, 1),
        VAR2(1, 2),
        VAR3(2, 3);

        private final Integer index;
        private final Integer level;
    }
}
