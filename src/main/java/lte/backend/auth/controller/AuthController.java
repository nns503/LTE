package lte.backend.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lte.backend.auth.dto.ReissueTokenDTO;
import lte.backend.auth.dto.request.JoinRequest;
import lte.backend.auth.dto.request.ReissueTokenRequest;
import lte.backend.auth.service.AuthService;
import lte.backend.auth.util.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController implements AuthApi {

    public static final String COOKIE_NAME_REFRESH = "refresh";

    private final JWTUtil jwtUtil;
    private final AuthService authService;

    @PostMapping("/join")
    public void join(
            @RequestBody @Validated JoinRequest request
    ) {
        authService.join(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> reissueToken(
            HttpServletRequest request
    ) {
        ReissueTokenDTO reissueTokenDTO = authService.reissueToken(new ReissueTokenRequest(extractCookieValue(request)));

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + reissueTokenDTO.accessToken())
                .header(HttpHeaders.SET_COOKIE, jwtUtil.createRefreshCookie(reissueTokenDTO.refreshToken()).toString())
                .build();
    }

    private String extractCookieValue(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(COOKIE_NAME_REFRESH))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
