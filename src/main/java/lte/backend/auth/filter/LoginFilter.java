package lte.backend.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.auth.domain.RefreshToken;
import lte.backend.auth.dto.request.LoginRequest;
import lte.backend.auth.repository.RefreshTokenRepository;
import lte.backend.auth.util.JWTUtil;
import lte.backend.common.exception.DefaultLTEException;
import lte.backend.common.exception.UnauthorizedLTEException;
import lte.backend.member.domain.Member;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginRequest loginRequest;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            loginRequest = objectMapper.readValue(inputStream, LoginRequest.class);
        } catch (IOException e) {
            throw new DefaultLTEException(e.getMessage());
        }
        String username = loginRequest.username();
        String password = loginRequest.password();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        AuthMember authMember = (AuthMember) authResult.getPrincipal();
        Long userId = authMember.getUserId();
        String username = authMember.getUsername();
        String role = authResult.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElseThrow(() -> new UnauthorizedLTEException("권한이 존재하지 않습니다."));

        String accessToken = jwtUtil.createAccessToken(userId, username, role);
        String refreshToken = jwtUtil.createRefreshToken(userId, username, role);
        saveRefreshTokenToRepository(userId, refreshToken);

        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        response.addHeader(HttpHeaders.SET_COOKIE, jwtUtil.createRefreshCookie(refreshToken).toString());
    }

    private void saveRefreshTokenToRepository(Long userId, String refreshToken) {
        refreshTokenRepository.save(RefreshToken.builder()
                .member(new Member(userId))
                .token(refreshToken)
                .expiration(jwtUtil.getRefreshTokenExpiration(refreshToken))
                .build());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        throw new UnauthorizedLTEException("로그인에 실패하였습니다.");
    }
}
