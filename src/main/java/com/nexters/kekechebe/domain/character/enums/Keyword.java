package com.nexters.kekechebe.domain.character.enums;


import java.util.List;

public class Keyword {
    private static final List<String> KEYWORD_LIST = List.of("열정적인", "꼼꼼한", "사려깊은", "창의적인"
                                                                , "사교적인", "긍정적인", "유연한", "책임감 있는"
                                                                , "비판적인", "성실한", "관대한", "자신감 있는"
                                                                , "협력적인", "여유로운", "따뜻한", "냉철한");

    public static boolean areKeywordsValid(List<Integer> keywords) {
        for (int index : keywords) {
            if (index < 0 || index >= KEYWORD_LIST.size()) {
                return false;
            }
        }
        return true;
    }
}
