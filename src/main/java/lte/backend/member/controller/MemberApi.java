package lte.backend.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import lte.backend.member.dto.request.UpdateNicknameRequest;
import lte.backend.member.dto.request.UpdatePasswordRequest;
import lte.backend.member.dto.response.GetMemberInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/members")
@Tag(name = "Member", description = "회원 로직 관리")
public interface MemberApi {

    @Operation(summary = "닉네임 수정 요청")
    @PutMapping("/nickname")
    ResponseEntity<Void> updateNickname(
            @Validated @RequestBody UpdateNicknameRequest request,
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(summary = "비밀번호 수정 요청")
    @PutMapping("/password")
    ResponseEntity<Void> updatePassword(
            @Validated @RequestBody UpdatePasswordRequest request,
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(summary = "회원 정보 요청")
    @GetMapping("/{memberId}/info")
    ResponseEntity<GetMemberInfoResponse> getMemberInfo(
            @Parameter(description = "멤버 ID") @PathVariable Long memberId
    );
}
