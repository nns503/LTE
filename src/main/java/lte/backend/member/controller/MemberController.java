package lte.backend.member.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.member.dto.request.UpdateNicknameRequest;
import lte.backend.member.dto.request.UpdatePasswordRequest;
import lte.backend.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PutMapping("/nickname")
    public ResponseEntity<Void> updateNickname(
            @Validated @RequestBody UpdateNicknameRequest request,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        memberService.updateNickname(request, authMember.getUserId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @Validated @RequestBody UpdatePasswordRequest request,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        memberService.updatePassword(request, authMember.getUserId());
        return ResponseEntity.ok().build();
    }
}
