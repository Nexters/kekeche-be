package com.nexters.kekechebe.domain.character.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SpecialtyResponse {
    List<SpecialtyDetail> specialties;
}
