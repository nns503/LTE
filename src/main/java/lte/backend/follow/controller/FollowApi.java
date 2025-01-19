package lte.backend.follow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import lte.backend.follow.dto.response.GetFollowCountResponse;
import lte.backend.follow.dto.response.GetFolloweeListResponse;
import lte.backend.follow.dto.response.GetFolloweePostsResponse;
import lte.backend.follow.dto.response.GetFollowerListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/members")
@Tag(name = "Follow", description = "팔로우 로직 관리")
public interface FollowApi {

    @Operation(summary = "팔로우 요청")
    @PostMapping("/{memberId}/follow")
    ResponseEntity<Void> followMember(
            @Parameter(description = "멤버 ID") @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(summary = "언팔로우 요청")
    @DeleteMapping("/{memberId}/follow")
    ResponseEntity<Void> unfollowMember(
            @Parameter(description = "멤버 ID") @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(summary = "팔로워 목록 조회")
    @GetMapping("/{memberId}/follower")
    ResponseEntity<GetFollowerListResponse> getFollowerList(
            @Parameter(description = "멤버 ID") @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "팔로위 목록 조회")
    @GetMapping("/{memberId}/followee")
    ResponseEntity<GetFolloweeListResponse> getFolloweeList(
            @Parameter(description = "멤버 ID") @PathVariable Long memberId,
            @AuthenticationPrincipal AuthMember authMember,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "팔로위 최신 게시글 조회")
    @GetMapping("/followee/posts")
    ResponseEntity<GetFolloweePostsResponse> getFolloweePosts(
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(summary = "팔로위 및 팔로워 수 조회")
    @GetMapping("/{memberId}/follow/count")
    ResponseEntity<GetFollowCountResponse> getFollowCount(
            @Parameter(description = "멤버 ID") @PathVariable Long memberId
    );
}
