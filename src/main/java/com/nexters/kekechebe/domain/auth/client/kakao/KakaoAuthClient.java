package com.nexters.kekechebe.domain.auth.client.kakao;

import com.nexters.kekechebe.domain.auth.dto.response.TokenResponse;
import com.nexters.kekechebe.domain.auth.dto.response.UserInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Component
public class KakaoAuthClient {
    @Value("${oauth.kakao.client-id}")
    private String clientId;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken(String code, String redirectUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        ResponseEntity<TokenResponse> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                TokenResponse.class
        );

        TokenResponse tokenDto = Objects.requireNonNull(response.getBody());

        String accessToken = tokenDto.getAccessToken();
        log.info("Auth Service >> access token : {}", accessToken);
        return accessToken;
    }

    public UserInfoResponse getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        ResponseEntity<UserInfoResponse> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                UserInfoResponse.class
        );

        UserInfoResponse userInfoDto = Objects.requireNonNull(response.getBody());
        Long id = userInfoDto.getId();
        String nickname = userInfoDto.getNickname();
        String email = userInfoDto.getEmail();

        log.info("Auth Service >> user info : {}, {}, {}", id, nickname, email);

        return userInfoDto;
    }
}
