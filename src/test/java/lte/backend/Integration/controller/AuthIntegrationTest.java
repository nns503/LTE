package lte.backend.Integration.controller;

import jakarta.servlet.http.Cookie;
import lte.backend.Integration.fixture.IntegrationFixture;
import lte.backend.auth.domain.RefreshToken;
import lte.backend.auth.dto.request.JoinRequest;
import lte.backend.auth.dto.request.LoginRequest;
import lte.backend.auth.repository.RefreshTokenRepository;
import lte.backend.auth.util.JWTUtil;
import lte.backend.follow.domain.Follow;
import lte.backend.follow.repository.FollowRepository;
import lte.backend.member.domain.Member;
import lte.backend.member.repository.MemberRepository;
import lte.backend.util.IntegrationTest;
import lte.backend.util.WithMockCustomMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthIntegrationTest extends IntegrationTest {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private Member member1;

    @BeforeEach
    void setUp() {
        member1 = IntegrationFixture.testMember1;
    }

    @Test
    @DisplayName("OK : 회원가입 성공")
    void join() throws Exception {
        JoinRequest request = new JoinRequest("join503",
                "join1234!!",
                "나회원가입",
                "https://example.com/profile.jpg");
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(post("/api/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        assertThat(memberRepository.existsByUsername(request.username())).isTrue();
    }

    @Test
    @DisplayName("OK : 로그인 성공")
    void login() throws Exception {
        LoginRequest request = new LoginRequest(member1.getUsername(), member1.getPassword());
        String body = objectMapper.writeValueAsString(request);

        MvcResult result = mvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        String accessToken = result.getResponse().getHeader("Authorization");
        Cookie refreshCookie = Arrays.stream(result.getResponse().getCookies())
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .findFirst()
                .orElseThrow(AssertionError::new);

        assertThat(accessToken).isNotNull();
        assertThat(refreshCookie).isNotNull();
        assertThat(jwtUtil.isValidToken(accessToken.split(" ")[1])).isTrue();
        assertThat(jwtUtil.isValidToken(refreshCookie.getValue())).isTrue();
    }

    @Test
    @DisplayName("OK : 로그아웃 성공")
    void logout() throws Exception {
        String accessToken = jwtUtil.createAccessToken(member1.getId(), member1.getUsername(), member1.getRole().name());
        String refreshToken = jwtUtil.createRefreshToken(member1.getId(), member1.getUsername(), member1.getRole().name());
        MvcResult result = mvc.perform(post("/api/logout")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .cookie(toServletCookie(jwtUtil.createRefreshCookie(refreshToken))))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getHeader("Authorization")).isNull();
        assertThat(Arrays.stream(result.getResponse().getCookies())
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .findFirst()).isEmpty();
    }

    @Test
    @DisplayName("OK : 리프레쉬 토큰 재발급")
    void reissueToken() throws Exception {
        String accessToken = jwtUtil.createAccessToken(member1.getId(), member1.getUsername(), member1.getRole().name());
        String refreshToken = jwtUtil.createRefreshToken(member1.getId(), member1.getUsername(), member1.getRole().name());
        refreshTokenRepository.save(RefreshToken.builder()
                .member(new Member(member1.getId()))
                .token(refreshToken)
                .expiration(jwtUtil.getRefreshTokenExpiration(refreshToken))
                .build());

        Cookie requestCookie = toServletCookie(jwtUtil.createRefreshCookie(refreshToken));
        MvcResult result = mvc.perform(post("/api/refresh")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .cookie(requestCookie))
                .andExpect(status().isOk())
                .andReturn();

        Cookie refreshCookie = Arrays.stream(result.getResponse().getCookies())
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .findFirst()
                .orElseThrow(AssertionError::new);
        assertThat(refreshCookie).isNotNull();
        assertThat(refreshCookie).isNotEqualTo(requestCookie);
        assertThat(jwtUtil.isValidToken(refreshCookie.getValue())).isTrue();
    }

    @Test
    @WithMockCustomMember
    @DisplayName("OK : 회원 삭제 성공")
    void deleteMember() throws Exception {
        Member member2 = IntegrationFixture.testMember2;
        Member member3 = IntegrationFixture.testMember3;
        memberRepository.save(member2);
        memberRepository.save(member3);
        followRepository.save(Follow.builder().
                followee(member1).
                follower(member2).
                build());
        followRepository.save(Follow.builder().
                followee(member1).
                follower(member3).
                build());
        followRepository.save(Follow.builder().
                followee(member2).
                follower(member1).
                build());
        followRepository.save(Follow.builder().
                followee(member2).
                follower(member3).
                build());

        mvc.perform(delete("/api/members"))
                .andExpect(status().isOk());

        assertThat(memberRepository.existsByUsername(member1.getUsername())).isFalse();
        assertThat(followRepository.count()).isEqualTo(1L);
        assertThat(followRepository.countByFollowerId(member1.getId())).isEqualTo(0);
        assertThat(followRepository.countByFolloweeId(member1.getId())).isEqualTo(0);
    }

    private Cookie toServletCookie(ResponseCookie responseCookie) {
        Cookie servletCookie = new Cookie(responseCookie.getName(), responseCookie.getValue());
        servletCookie.setPath(responseCookie.getPath());
        servletCookie.setSecure(responseCookie.isSecure());
        servletCookie.setHttpOnly(responseCookie.isHttpOnly());
        servletCookie.setMaxAge((int) responseCookie.getMaxAge().getSeconds());
        return servletCookie;
    }

}
