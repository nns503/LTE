package lte.backend.post.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.post.dto.request.CreatePostRequest;
import lte.backend.post.dto.request.UpdatePostRequest;
import lte.backend.post.dto.response.CreatePostResponse;
import lte.backend.post.dto.response.GetPostResponse;
import lte.backend.post.dto.response.UpdatePostResponse;
import lte.backend.post.service.PostService;
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
        CreatePostResponse response = postService.create(request, authMember.getUserId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> update(
            @Validated @RequestBody UpdatePostRequest request,
            @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        UpdatePostResponse response = postService.update(request, authMember.getUserId(), postId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long postId,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        postService.delete(authMember.getUserId(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPost(
            @PathVariable Long postId
    ) {
        GetPostResponse response = postService.getPost(postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public void getPosts() {

    }
}
