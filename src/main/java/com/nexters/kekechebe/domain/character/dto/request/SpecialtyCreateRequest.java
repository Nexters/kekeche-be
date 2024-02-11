package com.nexters.kekechebe.domain.character.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class SpecialtyCreateRequest {
    @Valid
    @Size(min = 1)
    List<SpecialtyInfo> specialties;
}
