package lte.backend.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.post.dto.PostDTO;
import lte.backend.util.SliceInfo;
import org.springframework.data.domain.Slice;

import java.util.List;

@Schema(description = "게시글 목록 조회 응답 데이터")
public record GetPostsResponse(
        @Schema(description = "조회 게시글 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
        List<PostDTO> posts,
        @Schema(description = "슬라이스 페이징 정보", requiredMode = Schema.RequiredMode.REQUIRED)
        SliceInfo sliceInfo
) {

    public static GetPostsResponse from(Slice<PostDTO> slicePosts) {
        return new GetPostsResponse(slicePosts.getContent(), SliceInfo.from(slicePosts));
    }
}
