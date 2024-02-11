package com.nexters.kekechebe.domain.character.enums;

import java.util.List;

import com.nexters.kekechebe.domain.character.dto.request.CharacterCreateRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.nexters.kekechebe.domain.character.enums.Keyword.areKeywordsValid;

public class CharacterAsset {
    private static final List<String> SHAPE_LIST = List.of("Circle", "Square", "Triangle");
    private static final List<String> COLOR_LIST = List.of("Red", "Blue", "Yellow", "Purple", "Green", "Pink");
    private static final List<String> ITEM_LIST = List.of("Laptop", "Exercise", "Money", "Pencil", "Book");
    public static final int NO_ITEM = -1;

    private static boolean isValidIndex(int index, List<String> list) {
        return index >= 0 && index < list.size();
    }

    private static boolean isShapeValid(int shapeIdx) {
        return isValidIndex(shapeIdx, SHAPE_LIST);
    }

    private static boolean isColorValid(int colorIdx) {
        return isValidIndex(colorIdx, COLOR_LIST);
    }

    private static boolean isItemValid(int itemIdx) {
        return itemIdx == NO_ITEM || isValidIndex(itemIdx, ITEM_LIST);
    }

    public static void validateCharacterAsset(CharacterCreateRequest request) {
        if (!isShapeValid(request.getShapeIdx())) {
            throw new IllegalArgumentException("잘못된 캐릭터 에셋 입니다.(body)");
        }
        if (!isColorValid(request.getColorIdx())) {
            throw new IllegalArgumentException("잘못된 캐릭터 에셋 입니다.(color)");
        }
        if (!isItemValid(request.getItemIdx())) {
            throw new IllegalArgumentException("잘못된 캐릭터 에셋 입니다.(item)");
        }
        if (!areKeywordsValid(request.getKeywords())) {
            throw new IllegalArgumentException("잘못된 키워드 입니다.");
        }
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
