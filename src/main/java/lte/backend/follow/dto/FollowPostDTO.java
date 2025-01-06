package lte.backend.follow.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "팔로위들의 게시글 정보 데이터")
public record FollowPostDTO(
        @Schema(description = "게시글 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long postId,
        @Schema(description = "게시글 제목", example = "테스트제목", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @Schema(description = "작성자 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long authorId,
        @Schema(description = "작성자 닉네임", example = "나테스트", requiredMode = Schema.RequiredMode.REQUIRED)
        String author
) {
}
