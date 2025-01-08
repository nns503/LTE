package lte.backend.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lte.backend.auth.domain.AuthMember;
import lte.backend.auth.dto.request.JoinRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
@Tag(name = "Auth", description = "인증 로직 관리")
public interface AuthApi {

    @Operation(summary = "회원 회원가입 요청")
    @PostMapping("/join")
    ResponseEntity<Void> join(
            JoinRequest joinRequest
    );

    @Operation(summary = "회원 계정 삭제 요청")
    @DeleteMapping("/members")
    ResponseEntity<Void> deleteMember(
            AuthMember member
    );

    @Operation(summary = "리프레쉬 토큰 갱신 요청")
    @PostMapping("/refresh")
    ResponseEntity<Void> reissueToken(
            HttpServletRequest request
    );
}
