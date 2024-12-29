package lte.backend.follow.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.follow.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members/{followeeId}")
public class FollowController implements FollowApi {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<Void> followMember(
            @PathVariable Long followeeId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        followService.followMember(followeeId, authMember.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/follow")
    public ResponseEntity<Void> unfollowMember(
            @PathVariable Long followeeId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        followService.unfollowMember(followeeId, authMember.getUserId());
        return ResponseEntity.ok().build();
    }
}
