package com.nexters.kekechebe.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class TokenResponse {
    @JsonProperty("access_token")
    private final String accessToken;
}
