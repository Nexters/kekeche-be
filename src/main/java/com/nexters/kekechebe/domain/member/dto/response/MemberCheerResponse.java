package com.nexters.kekechebe.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MemberCheerResponse {
    private final Long memberId;
    private final Integer cheerCount;
}
