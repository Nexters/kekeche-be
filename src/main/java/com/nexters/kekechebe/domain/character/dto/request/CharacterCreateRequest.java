package com.nexters.kekechebe.domain.character.dto.request;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CharacterCreateRequest {
    private static final String CHARACTER_NAME_REGEX = "^[가-힣a-zA-Z\\s]{1,8}$";

    @NotBlank(message = "캐릭터 이름을 입력해 주세요.")
    @Pattern(regexp = CHARACTER_NAME_REGEX, message = "캐릭터 이름은 한글, 영문 대소문자, 공백 포함 최대 8자만 허용됩니다.")
    private String name;
    @NotNull(message = "캐릭터 모습을 선택해 주세요.")
    @Min(value = 0, message = "캐릭터 모습 index는 0 이상이어야 합니다.")
    @Max(value = 2, message = "캐릭터 모습 index는 2 이하여야 합니다.")
    private Integer shapeIdx;
    @NotNull(message = "캐릭터 컬러를 선택해 주세요.")
    @Min(value = 0, message = "캐릭터 컬러 index는 0 이상이어야 합니다.")
    @Max(value = 5, message = "캐릭터 컬러 index는 5 이하여야 합니다.")
    private Integer colorIdx;
    @NotNull(message = "캐릭터 키워드를 선택해 주세요.")
    @Size(min = 1, max = 3, message = "캐릭터 키워드는 최소 1개, 최대 3개여야 합니다.")
    private List<Integer> keywords;
    @Min(value = -1, message = "아이템 index는 -1 이상이어야 합니다.")
    @Max(value = 4, message = "아이템 index는 4 이하여야 합니다.")
    private Integer itemIdx;


}
