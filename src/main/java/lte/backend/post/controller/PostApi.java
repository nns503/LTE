package lte.backend.post.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/posts")
@Tag(name = "Post", description = "게시글 로직 관리")
public interface PostApi {

    @Operation(summary = "게시글 작성 요청")
    @PostMapping
    ResponseEntity<CreatePostResponse> create(
            CreatePostRequest request,
            AuthMember authMember
    );

    @Operation(summary = "게시글 수정 요청")
    @PutMapping("/{postId}")
    ResponseEntity<UpdatePostResponse> update(
            UpdatePostRequest request,
            Long postId,
            AuthMember authMember
    );

    @Operation(summary = "게시글 삭제 요청")
    @DeleteMapping("/{postId}")
    ResponseEntity<Void> delete(
            Long postId,
            AuthMember authMember
    );

    @Operation(summary = "게시글 단건 조회")
    @GetMapping("/{postId}")
    ResponseEntity<GetPostResponse> getPost(
            AuthMember authMember,
            Long postId
    );

    @Operation(summary = "게시글 목록 조회")
    @GetMapping
    ResponseEntity<GetPostsResponse> getPosts(
            int page,
            int size,
            GetPostsRequest request
    );
}
