package lte.backend.member.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.member.dto.request.UpdateNicknameRequest;
import lte.backend.member.dto.request.UpdatePasswordRequest;
import lte.backend.member.dto.request.UpdateProfileUrlRequest;
import lte.backend.member.dto.response.GetMemberInfoResponse;
import lte.backend.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController implements MemberApi {

    private final MemberService memberService;

    @PutMapping("/nickname")
    public ResponseEntity<Void> updateNickname(
            @Validated @RequestBody UpdateNicknameRequest request,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        memberService.updateNickname(request, authMember.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @Validated @RequestBody UpdatePasswordRequest request,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        memberService.updatePassword(request, authMember.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/profileUrl")
    public ResponseEntity<Void> updateProfileUrl(
            @Validated @RequestBody UpdateProfileUrlRequest request,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        memberService.updateProfileUrl(request, authMember.getMemberId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}/info")
    public ResponseEntity<GetMemberInfoResponse> getMemberInfo(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(memberService.getMemberInfo(memberId));
    }
}
