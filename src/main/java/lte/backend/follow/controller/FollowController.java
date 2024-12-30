package lte.backend.follow.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.follow.dto.response.GetFolloweeListResponse;
import lte.backend.follow.dto.response.GetFollowerListResponse;
import lte.backend.follow.service.FollowService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members/{memberId}")
public class FollowController implements FollowApi {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<Void> followMember(
            @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        followService.followMember(memberId, authMember.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/follow")
    public ResponseEntity<Void> unfollowMember(
            @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        followService.unfollowMember(memberId, authMember.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/follower")
    public ResponseEntity<GetFollowerListResponse> getFollowerList(
            @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        GetFollowerListResponse response = followService.getFollowerList(authMember.getUserId(), memberId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/followee")
    public ResponseEntity<GetFolloweeListResponse> getFolloweeList(
            @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        GetFolloweeListResponse response = followService.getFolloweeList(authMember.getUserId(), memberId, pageable);
        return ResponseEntity.ok(response);
    }
}
