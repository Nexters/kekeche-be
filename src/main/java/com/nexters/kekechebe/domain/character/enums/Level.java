package com.nexters.kekechebe.domain.character.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Level {
    LEVEL1(1, 0, 12),
    LEVEL2(2, 12, 24),
    LEVEL3(3, 24, 48);

    private final Integer level;
    private final Integer expMin;
    private final Integer expMax;

    public static Integer getNextExpThreshold(int totalExp) {
        for (Level level : values()) {
            if (totalExp < level.getExpMax()) {
                return level.getExpMax() - level.getExpMin();
            }
        }
        return totalExp;
    }

    public static Integer getCurrentExpThreshold(int totalExp) {
        for (Level level : values()) {
            if (totalExp < level.getExpMax()) {
                return totalExp - level.getExpMin();
            }
        }
        return totalExp;
    }

    public static Integer getUpdatedLevel(int totalExp) {
        for (Level level : values()) {
            if (totalExp < level.getExpMax()) {
                return level.getLevel();
            }
        }
        return totalExp;
    }
}
