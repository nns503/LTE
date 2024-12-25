package lte.backend.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import lte.backend.post.dto.request.CreatePostRequest;
import lte.backend.post.dto.request.UpdatePostRequest;
import lte.backend.post.dto.response.CreatePostResponse;
import lte.backend.post.dto.response.GetPostResponse;
import lte.backend.post.dto.response.UpdatePostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/posts")
@Tag(name = "Post", description = "게시글 로직 관리")
public interface PostApi {

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "401"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "게시글 작성 요청")
    ResponseEntity<CreatePostResponse> create(
            CreatePostRequest request,
            AuthMember authMember
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "401"),
                    @ApiResponse(responseCode = "403"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "게시글 수정 요청")
    ResponseEntity<UpdatePostResponse> update(
            UpdatePostRequest request,
            Long postId,
            AuthMember authMember
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "401"),
                    @ApiResponse(responseCode = "403"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "게시글 삭제 요청")
    ResponseEntity<Void> delete(
            Long postId,
            AuthMember authMember
    );

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "401"),
                    @ApiResponse(responseCode = "404")
            }
    )
    @Operation(summary = "게시글 수정 요청")
    ResponseEntity<GetPostResponse> getPost(
            Long postId
    );
}
