package com.nexters.kekechebe.domain.memo.service;

import static com.nexters.kekechebe.util.character.LevelUtil.*;

import com.nexters.kekechebe.domain.character.entity.Character;
import com.nexters.kekechebe.util.character.LevelUtil;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class CharacterHelperService {
    private static final int EXP_UP_COUNT = 1;
    private static final int LEVEL_UP_COUNT = 1;

    public void updateExp(Character character) {
        character.updateExp(EXP_UP_COUNT);
    }

    public boolean isLevelUp(Character character) {
        LevelUtil.LevelInfo levelInfo = LevelUtil.getLevelInfo(character.getExp());
        if (character.getLevel() < levelInfo.getUpdatedLevel()) {
            updateCharacter(character);
            return true;
        }
        return false;
    }

    private void updateCharacter(Character character) {
        Integer nextLevel = character.updateLevel(LEVEL_UP_COUNT);
        character.updateVariation(getVariationByLevel(nextLevel));
    }
}
