package com.nexters.kekechebe.domain.character.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CharacterAsset {
    private Shape shape;
    private Color color;
    private Variation variation;

    @Getter
    @RequiredArgsConstructor
    public enum Shape {
        CIRCLE(0),
        SQUARE(1),
        TRIANGLE(2);

        private final Integer value;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Color {
        RED(0),
        BLUE(1),
        YELLOW(2),
        PURPLE(3),
        GREEN(4),
        PINK(5);

        private final Integer value;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Variation {
        VAR1(0, 1, 10),
        VAR2(1, 11, 20),
        VAR3(2, 21, 30);

        private final Integer index;
        private final Integer levelMin;
        private final Integer levelMax;
    }
}
