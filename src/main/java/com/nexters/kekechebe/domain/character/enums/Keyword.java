package com.nexters.kekechebe.domain.character.enums;


import java.util.List;

public class Keyword {
    private static final List<String> KEYWORD_LIST = List.of("다정한", "귀여운", "느긋한", "감성적인"
                                                                , "낭만적", "열정적", "대담한", "욕심 많은"
                                                                , "도전적", "단호한", "웃긴", "쾌활한"
                                                                , "사교적", "활발한", "긍정적", "조용한"
                                                                , "지혜로운", "신중한", "꼼꼼한", "성실한"
                                                                , "영리한", "분석적", "냉철한", "지적인"
                                                                , "현실적", "창의적", "독립적", "민감한"
                                                                , "솔직한", "독특한");

    public static boolean areKeywordsValid(List<Integer> keywords) {
        for (int index : keywords) {
            if (index < 0 || index >= KEYWORD_LIST.size()) {
                return false;
            }
        }
        return true;
    }
}
