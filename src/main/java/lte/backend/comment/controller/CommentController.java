package lte.backend.comment.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.comment.dto.request.CreateCommentRequest;
import lte.backend.comment.dto.request.UpdateCommentRequest;
import lte.backend.comment.dto.response.GetCommentsResponse;
import lte.backend.comment.service.CommentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController implements CommentApi {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> create(
            @Validated @RequestBody CreateCommentRequest request,
            @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        commentService.create(request, authMember.getMemberId(), postId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> update(
            @Validated @RequestBody UpdateCommentRequest request,
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long commentId
    ) {
        commentService.update(request, authMember.getMemberId(), commentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        commentService.delete(authMember.getMemberId(), commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<GetCommentsResponse> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(commentService.getComments(postId, pageable));
    }
}
