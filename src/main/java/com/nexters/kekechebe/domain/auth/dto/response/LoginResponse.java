package com.nexters.kekechebe.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class LoginResponse {
    private final Long memberId;
    private final String nickname;
    private final String accessToken;
}
