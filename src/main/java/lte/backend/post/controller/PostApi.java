package lte.backend.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import lte.backend.post.dto.request.CreatePostRequest;
import lte.backend.post.dto.request.GetPostsRequest;
import lte.backend.post.dto.request.UpdatePostRequest;
import lte.backend.post.dto.response.CreatePostResponse;
import lte.backend.post.dto.response.GetPostResponse;
import lte.backend.post.dto.response.GetPostsResponse;
import lte.backend.post.dto.response.UpdatePostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posts")
@Tag(name = "Post", description = "게시글 로직 관리")
public interface PostApi {

    @Operation(summary = "게시글 작성 요청")
    @PostMapping
    ResponseEntity<CreatePostResponse> create(
            @Validated @RequestBody CreatePostRequest request,
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(summary = "게시글 수정 요청")
    @PutMapping("/{postId}")
    ResponseEntity<UpdatePostResponse> update(
            @Validated @RequestBody UpdatePostRequest request,
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(summary = "게시글 삭제 요청")
    @DeleteMapping("/{postId}")
    ResponseEntity<Void> delete(
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(summary = "게시글 단건 조회")
    @GetMapping("/{postId}")
    ResponseEntity<GetPostResponse> getPost(
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    );

    @Operation(summary = "게시글 목록 조회")
    @GetMapping
    ResponseEntity<GetPostsResponse> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @Validated GetPostsRequest request
    );
}
