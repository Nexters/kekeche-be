package com.nexters.kekechebe.domain.character.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SpecialtyInfo {
    @NotBlank
    private String content;
}
