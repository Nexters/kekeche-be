package com.nexters.kekechebe.domain.character.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Level {
    LEVEL1(1, 11),
    LEVEL2(12, 24),
    LEVEL3(25, 99);

    private final Integer expMin;
    private final Integer expMax;

    public static Integer getNextLevelThreshold(int currentLevel) {
        for (Level level : values()) {
            if (currentLevel < level.getExpMax()) {
                return level.getExpMax();
            }
        }
        return currentLevel;
    }
}
