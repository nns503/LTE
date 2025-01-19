package lte.backend.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import lte.backend.comment.dto.request.CreateCommentRequest;
import lte.backend.comment.dto.request.UpdateCommentRequest;
import lte.backend.comment.dto.response.GetCommentsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posts/{postId}/comments")
@Tag(name = "COMMENT", description = "댓글 로직 관리")
public interface CommentApi {

    @Operation(
            summary = "댓글 작성"
    )
    @PostMapping
    ResponseEntity<Void> create(
            @Validated @RequestBody CreateCommentRequest request,
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(
            summary = "댓글 수정",
            parameters = {
                    @Parameter(name = "postId", description = "게시글 ID", in = ParameterIn.PATH)
            }
    )
    @PutMapping("/{commentId}")
    ResponseEntity<Void> update(
            @Validated @RequestBody UpdateCommentRequest request,
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long commentId
    );

    @Operation(
            summary = "댓글 삭제",
            parameters = {
                    @Parameter(name = "postId", description = "게시글 ID", in = ParameterIn.PATH)
            }
    )
    @DeleteMapping("/{commentId}")
    ResponseEntity<Void> delete(
            @Parameter(description = "댓글 ID") @PathVariable Long commentId,
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(summary = "댓글 목록 조회")
    @GetMapping
    ResponseEntity<GetCommentsResponse> getComments(
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    );
}
