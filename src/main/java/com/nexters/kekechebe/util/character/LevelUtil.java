package com.nexters.kekechebe.util.character;

import com.nexters.kekechebe.domain.character.enums.CharacterAsset;
import com.nexters.kekechebe.domain.character.enums.Level;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class LevelUtil {
    public static LevelInfo getLevelInfo(int totalExp) {
        for (Level level : Level.values()) {
            if (totalExp < level.getExpMax()) {
                int currentExpThreshold = totalExp - level.getExpMin();
                int nextExpThreshold = level.getExpMax() - level.getExpMin();
                return new LevelInfo(currentExpThreshold, nextExpThreshold, level.getLevel());
            }
        }
        Level lastLevel = Level.values()[Level.values().length - 1];
        int currentExpThreshold = totalExp - lastLevel.getExpMax();
        int nextExpThreshold = 99; // 최대 레벨 99
        return new LevelInfo(currentExpThreshold, nextExpThreshold, lastLevel.getLevel());
    }

    public static CharacterAsset.Variation getVariationByLevel(int level) {
        for (CharacterAsset.Variation variation : CharacterAsset.Variation.values()) {
            if (variation.getLevel().equals(level)) {
                return variation;
            }
        }
        return CharacterAsset.Variation.VAR3; // 3레벨 이상 VAR3
    }

    @Getter
    @AllArgsConstructor
    public static class LevelInfo {
        private final int currentExpThreshold;
        private final int nextExpThreshold;
        private final int updatedLevel;
    }
}
