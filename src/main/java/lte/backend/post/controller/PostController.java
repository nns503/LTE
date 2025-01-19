package lte.backend.post.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.post.dto.request.CreatePostRequest;
import lte.backend.post.dto.request.GetPostsRequest;
import lte.backend.post.dto.request.UpdatePostRequest;
import lte.backend.post.dto.response.CreatePostResponse;
import lte.backend.post.dto.response.GetPostResponse;
import lte.backend.post.dto.response.GetPostsResponse;
import lte.backend.post.dto.response.UpdatePostResponse;
import lte.backend.post.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController implements PostApi {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CreatePostResponse> create(
            @Validated @RequestBody CreatePostRequest request,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        return ResponseEntity.ok(postService.create(request, authMember.getMemberId()));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> update(
            @Validated @RequestBody UpdatePostRequest request,
            @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        return ResponseEntity.ok(postService.update(request, authMember.getMemberId(), postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        postService.delete(authMember.getMemberId(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        return ResponseEntity.ok(postService.getPost(authMember.getMemberId(), postId));
    }

    @GetMapping
    public ResponseEntity<GetPostsResponse> getPosts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @Validated GetPostsRequest request
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return ResponseEntity.ok(postService.getPosts(pageable, request));
    }
}
