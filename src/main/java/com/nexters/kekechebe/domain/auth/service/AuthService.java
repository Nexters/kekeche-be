package com.nexters.kekechebe.domain.auth.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.nexters.kekechebe.domain.auth.dto.response.TokenResponse;
import com.nexters.kekechebe.domain.auth.dto.response.LoginResponse;
import com.nexters.kekechebe.domain.auth.dto.response.UserInfoResponse;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.member.repository.MemberRepository;
import com.nexters.kekechebe.jwt.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Value("${oauth.kakao.client-id}")
    private String clientId;
    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;

    public LoginResponse kakaoLogin(String code, HttpServletResponse response) {
        String accessToken = getToken(code);
        UserInfoResponse userInfoDto = getKakaoUserInfo(accessToken);
        Member kakaoUser = registerKakaoUserIfNeeded(userInfoDto);
        String createToken =  jwtUtil.createToken(kakaoUser.getEmail());
        // response.addHeader(JwtUtil.AUTHORIZATION_HEADER, createToken);

        return LoginResponse.builder()
            .memberId(kakaoUser.getId())
            .nickname(kakaoUser.getNickname())
            .accessToken(createToken)
            .build();
    }

    private String getToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TokenResponse> response = restTemplate.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            TokenResponse.class
        );

        TokenResponse tokenDto = response.getBody();
        assert tokenDto != null;
        String accessToken = tokenDto.getAccessToken();
        log.info("Auth Service >> access token : {}", accessToken);
        return accessToken;
    }

    private UserInfoResponse getKakaoUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserInfoResponse> response = restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoUserInfoRequest,
            UserInfoResponse.class
        );

        UserInfoResponse userInfoDto = response.getBody();
        assert userInfoDto != null;
        Long id = userInfoDto.getId();
        String nickname = userInfoDto.getNickname();
        String email = userInfoDto.getEmail();

        log.info("Auth Service >> user info : {}, {}, {}", id, nickname, email);

        return userInfoDto;
    }

    private Member registerKakaoUserIfNeeded(UserInfoResponse userInfoDto) {
        Long kakaoId = userInfoDto.getId();
        String email = userInfoDto.getEmail();
        return memberRepository.findByKakaoIdAndEmail(kakaoId, email)
            .orElseGet(() -> createMember(userInfoDto));
    }

    private Member createMember(UserInfoResponse userInfoDto) {
        Member kakaoUser = Member.builder()
                        .email(userInfoDto.getEmail())
                        .nickname(userInfoDto.getNickname())
                        .kakaoId(userInfoDto.getId())
                        .build();
        return memberRepository.save(kakaoUser);
    }
}
