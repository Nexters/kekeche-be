package com.nexters.kekechebe.domain.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class KakaoUserInfo {
    private final Long id;
    private final String nickname;
    private final String email;
}
