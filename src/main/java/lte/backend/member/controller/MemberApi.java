package lte.backend.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import lte.backend.member.dto.request.UpdateNicknameRequest;
import lte.backend.member.dto.request.UpdatePasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/members")
@Tag(name = "Member", description = "회원 로직 관리")
public interface MemberApi {

    @Operation(summary = "닉네임 수정 요청")
    @PutMapping("/nickname")
    ResponseEntity<Void> updateNickname(
            UpdateNicknameRequest request,
            AuthMember authMember
    );

    @Operation(summary = "비밀번호 수정 요청")
    @PutMapping("/password")
    ResponseEntity<Void> updatePassword(
            UpdatePasswordRequest request,
            AuthMember authMember
    );
}
