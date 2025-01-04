package lte.backend.follow.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.follow.dto.FollowPostDTO;

import java.util.List;

@Schema(description = "팔로위 게시글 목록 조회 응답 데이터")
public record GetFolloweePostsResponse(
        @Schema(description = "팔로위 게시글 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
        List<FollowPostDTO> posts
) {
    public static GetFolloweePostsResponse from(List<FollowPostDTO> posts) {
        return new GetFolloweePostsResponse(posts);
    }
}
