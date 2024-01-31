package com.nexters.kekechebe.domain.character.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Item {
    LAPTOP(0),
    EXERCISE(1),
    MONEY(2),
    PENCIL(3),
    BOOK(4),
    BLANK(5)
    ;

    private final Integer value;
}
