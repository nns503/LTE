package lte.backend.like.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.like.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{postId}")
public class LikeController implements LikeApi {

    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<Void> likePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        likeService.likePost(postId, authMember.getUserId());
        return ResponseEntity.ok().build();
    }
}
