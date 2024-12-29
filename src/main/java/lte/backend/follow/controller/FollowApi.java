package lte.backend.follow.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
}
