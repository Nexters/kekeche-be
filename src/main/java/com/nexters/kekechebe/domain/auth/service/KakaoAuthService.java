package com.nexters.kekechebe.domain.auth.service;


import com.nexters.kekechebe.domain.auth.client.kakao.KakaoAuthClient;
import com.nexters.kekechebe.domain.auth.dto.response.LoginResponse;
import com.nexters.kekechebe.domain.auth.dto.response.UserInfoResponse;
import com.nexters.kekechebe.domain.member.entity.Member;
import com.nexters.kekechebe.domain.member.repository.MemberRepository;
import com.nexters.kekechebe.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KakaoAuthService implements AuthService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final KakaoAuthClient kakaoAuthClient;

    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${oauth.kakao.character-redirect-uri}")
    private String characterRedirectUri;

    @Override
    @Transactional
    public LoginResponse login(String code) {
        return kakaoLogin(code, redirectUri);
    }

    @Transactional
    public LoginResponse characterLogin(String code) {
        return kakaoLogin(code, characterRedirectUri);
    }

    private LoginResponse kakaoLogin(String code, String redirectUri) {
        String accessToken = kakaoAuthClient.getAccessToken(code, redirectUri);
        UserInfoResponse userInfoDto = kakaoAuthClient.getUserInfo(accessToken);
        Member kakaoUser = registerKakaoUserIfNeeded(userInfoDto);
        String createToken =  jwtUtil.createToken(kakaoUser.getEmail());

        return LoginResponse.builder()
                .memberId(kakaoUser.getId())
                .nickname(kakaoUser.getNickname())
                .accessToken(createToken)
                .build();
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
                .cheerCount(0)
                .build();
        return memberRepository.save(kakaoUser);
    }
}
