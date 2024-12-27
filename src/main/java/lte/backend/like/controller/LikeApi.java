package lte.backend.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/posts/{postId}")
@Tag(name = "Like", description = "좋아요 로직 관리")
public interface LikeApi {

    @Operation(summary = "게시글 좋아요 요청")
    @PostMapping("/like")
    ResponseEntity<Void> likePost(
            Long postId,
            AuthMember authMember
    );
}
