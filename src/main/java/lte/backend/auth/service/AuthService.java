package lte.backend.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lte.backend.auth.domain.RefreshToken;
import lte.backend.auth.dto.ReissueTokenDTO;
import lte.backend.auth.dto.request.JoinRequest;
import lte.backend.auth.dto.request.ReissueTokenRequest;
import lte.backend.auth.exception.NicknameDuplicationException;
import lte.backend.auth.exception.RefreshTokenException;
import lte.backend.auth.exception.UsernameDuplicationException;
import lte.backend.auth.repository.RefreshTokenRepository;
import lte.backend.auth.util.JWTUtil;
import lte.backend.follow.repository.FollowRepository;
import lte.backend.member.domain.Member;
import lte.backend.member.domain.MemberRole;
import lte.backend.member.exception.MemberNotFoundException;
import lte.backend.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {

    private static final String CATEGORY_REFRESH = "refresh";

    private final Clock clock;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Transactional
    public void join(JoinRequest request) {
        String username = request.username();
        String nickname = request.nickname();

        validateDuplicateUsername(username);
        validateDuplicateNickname(nickname);

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname())
                .profileUrl(request.profileUrl())
                .role(MemberRole.ROLE_USER)
                .isDeleted(false)
                .build();

        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        followRepository.deleteFollowsByMemberId(memberId);
        memberRepository.delete(member);
    }

    @Transactional
    public ReissueTokenDTO reissueToken(ReissueTokenRequest request) {
        String refreshToken = request.refreshToken();
        validateRefreshToken(refreshToken);

        Long userId = jwtUtil.getUserId(refreshToken);
        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken).toString();
        String accessToken = jwtUtil.createAccessToken(userId, username, role);
        String newRefreshToken = jwtUtil.createRefreshToken(userId, username, role);

        refreshTokenRepository.deleteByToken(refreshToken);
        saveRefreshTokenToRepository(userId, newRefreshToken);

        return new ReissueTokenDTO(accessToken, newRefreshToken);
    }

    @Transactional
    public void removeExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(LocalDateTime.now(clock));
    }

    private void validateDuplicateNickname(String nickname) {
        if(memberRepository.existsByNickname(nickname)) {
            throw new NicknameDuplicationException();
        }
    }

    private void validateDuplicateUsername(String username) {
        if(memberRepository.existsByUsername(username)) {
            throw new UsernameDuplicationException();
        }
    }

    private void validateRefreshToken(String refresh) {
        validateValidToken(refresh);
        validateTokenCategory(refresh);
        validateTokenRepository(refresh);
    }

    private void validateTokenCategory(String tokenValue) {
        if (!jwtUtil.getCategory(tokenValue).equals(CATEGORY_REFRESH)) {
            log.info("카테고리가 다른 토큰");

            throw new RefreshTokenException();
        }
    }

    private void validateValidToken(String tokenValue) {
        if (!jwtUtil.isValidToken(tokenValue)) {
            log.info("유효하지 않는 토큰");
            throw new RefreshTokenException();
        }
    }

    private void validateTokenRepository(String tokenValue) {
        if (!refreshTokenRepository.existsByToken(tokenValue)) {
            log.info("저장소에 보관 되어있지 않은 토큰");
            throw new RefreshTokenException();
        }
    }

    private void saveRefreshTokenToRepository(Long userId, String refreshToken) {
        refreshTokenRepository.save(RefreshToken.builder()
                .member(new Member(userId))
                .token(refreshToken)
                .expiration(jwtUtil.getRefreshTokenExpiration(refreshToken))
                .build());
    }
}
