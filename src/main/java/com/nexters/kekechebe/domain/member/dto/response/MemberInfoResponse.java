package com.nexters.kekechebe.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class MemberInfoResponse {
    private final long memberId;
    private final String nickname;
    private final long characterCount;
    private final long memoCount;
}