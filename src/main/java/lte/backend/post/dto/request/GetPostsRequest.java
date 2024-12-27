package lte.backend.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.post.domain.PostSearchBy;
import lte.backend.post.domain.PostSortBy;

@Schema(description = "게시글 검색 요청 데이터")
public record GetPostsRequest(
        @Schema(description = "검색 내용 조건", example = "TITLE")
        PostSearchBy searchBy,
        @Schema(description = "검색 내용", example = "나테스트")
        String searchValue,
        @Schema(description = "정렬 조건", example = "DATE")
        PostSortBy sortBy
) {
}
