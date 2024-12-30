package lte.backend.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import lte.backend.comment.dto.request.CreateCommentRequest;
import lte.backend.comment.dto.request.UpdateCommentRequest;
import lte.backend.comment.dto.response.GetCommentsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posts/{postId}/comments")
@Tag(name = "COMMENT", description = "댓글 로직 관리")
public interface CommentApi {

    @Operation(summary = "댓글 작성")
    @PostMapping
    ResponseEntity<Void> create(
            CreateCommentRequest request,
            Long postId,
            AuthMember authMember
    );

    @Operation(summary = "댓글 수정")
    @PutMapping("/{commentId}")
    ResponseEntity<Void> update(
            UpdateCommentRequest request,
            Long commentId,
            AuthMember authMember
    );

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    ResponseEntity<Void> delete(
            Long commentId,
            AuthMember authMember
    );

    @Operation(summary = "댓글 목록 조회")
    @GetMapping
    ResponseEntity<GetCommentsResponse> getComments(
            Long postId,
            int page,
            int size
    );
}
