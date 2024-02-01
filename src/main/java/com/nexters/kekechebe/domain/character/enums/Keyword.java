package com.nexters.kekechebe.domain.character.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Keyword {
    PASSIONATE(0, "열정적"),
    METICULOUS(1, "꼼꼼한"),
    THOUGHTFUL(2, "사려깊은"),
    CREATIVE(3, "창의적인"),
    CREATIVE2(4, "창의적인"),
    SOCIABLE(5, "사교적인"),
    POSITIVE(6, "긍정적인"),
    FLEXIBILITY(7, "유연한"),
    RESPONSIBLE(8, "책임감 있는"),
    CRITICAL(9, "비판적인"),
    SINCERE(10, "성실한"),
    GENEROUS(11, "관대한"),
    CONFIDENT(12, "자신감 있는"),
    COOPERATIVE(13, "협력적인"),
    FREE(2, "여유로운"),

    ;

    private final Integer index;
    private final String value;
}
