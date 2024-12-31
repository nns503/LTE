package lte.backend.auth.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lte.backend.member.domain.MemberRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JWTUtil {

    private final Clock clock;

    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.access_expiration}")
    private long accessExpiration;
    @Value("${spring.jwt.refresh_expiration}")
    private long refreshExpiration;

    public String createRefreshToken(Long userId, String username, String role) {
        Instant now = clock.instant();
        return Jwts.builder()
                .claim("category", "refresh")
                .claim("id", userId)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(refreshExpiration)))
                .signWith(getSignKey())
                .compact();
    }

    public String createAccessToken(Long userId, String username, String role) {
        Instant now = clock.instant();
        return Jwts.builder()
                .claim("category", "access")
                .claim("id", userId)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(accessExpiration)))
                .signWith(getSignKey())
                .compact();
    }

    public Long getUserId(String token) {
        return Jwts.parser().
                verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }

    public String getUsername(String token) {
        return Jwts.parser().
                verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username", String.class);
    }

    public MemberRole getRole(String token) {
        String roleString = Jwts.parser().
                verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);

        return MemberRole.valueOf(roleString);
    }

    public String getCategory(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("category", String.class);
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.info("JWT 서명이 잘못 되었습니다.", e);
        } catch (ExpiredJwtException e) {
            log.info("JWT 토큰이 만료 되었습니다.", e);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 JWT 토큰입니다.", e);
        } catch (Exception e) {
            log.info("알 수 없는 JWT 오류입니다.", e);
        }
        return false;
    }

    public ResponseCookie createRefreshCookie(String refreshToken) {
        return ResponseCookie.from("refresh", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshExpiration / 1000)
                .sameSite("Strict")
                .build();
    }


    public LocalDateTime getRefreshTokenExpiration(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .toInstant()
                .atZone(clock.getZone())
                .toLocalDateTime();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
