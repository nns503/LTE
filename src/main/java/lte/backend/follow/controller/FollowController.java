package lte.backend.follow.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.follow.dto.response.GetFolloweeListResponse;
import lte.backend.follow.dto.response.GetFolloweePostsResponse;
import lte.backend.follow.dto.response.GetFollowerListResponse;
import lte.backend.follow.service.FollowService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class FollowController implements FollowApi {

    private final FollowService followService;

    @PostMapping("/{memberId}/follow")
    public ResponseEntity<Void> followMember(
            @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        followService.followMember(memberId, authMember.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberId}/follow")
    public ResponseEntity<Void> unfollowMember(
            @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        followService.unfollowMember(memberId, authMember.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}/follower")
    public ResponseEntity<GetFollowerListResponse> getFollowerList(
            @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(followService.getFollowerList(authMember.getUserId(), memberId, pageable));
    }

    @GetMapping("/{memberId}/followee")
    public ResponseEntity<GetFolloweeListResponse> getFolloweeList(
            @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(followService.getFolloweeList(authMember.getUserId(), memberId, pageable));
    }

    @GetMapping("/followee/posts")
    public ResponseEntity<GetFolloweePostsResponse> getFolloweePosts(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        return ResponseEntity.ok(followService.getFolloweePostList(authMember.getUserId()));
    }
}
