package lte.backend.follow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import lte.backend.follow.dto.response.GetFolloweeListResponse;
import lte.backend.follow.dto.response.GetFollowerListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/members/{followeeId}")
@Tag(name = "Follow", description = "팔로우 로직 관리")
public interface FollowApi {

    @Operation(summary = "팔로우 요청")
    @PostMapping("/follow")
    ResponseEntity<Void> followMember(
            Long followeeId,
            AuthMember authMember
    );

    @Operation(summary = "언팔로우 요청")
    @DeleteMapping("/follow")
    ResponseEntity<Void> unfollowMember(
            Long followeeId,
            AuthMember authMember
    );

    @Operation(summary = "팔로워 목록 조회")
    @GetMapping("/follower")
    ResponseEntity<GetFollowerListResponse> getFollowerList(
            Long memberId,
            AuthMember authMember,
            int page,
            int size
    );

    @Operation(summary = "팔로위 목록 조회")
    @GetMapping("/followee")
    ResponseEntity<GetFolloweeListResponse> getFolloweeList(
            Long memberId,
            AuthMember authMember,
            int page,
            int size
    );
}
