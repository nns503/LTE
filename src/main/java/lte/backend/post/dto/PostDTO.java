package lte.backend.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "게시글 목록에서의 게시글 정보 데이터")
public record PostDTO(
        @Schema(description = "게시글 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long postId,
        @Schema(description = "게시글 제목", example = "테스트제목", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @Schema(description = "조회수", example = "7", requiredMode = Schema.RequiredMode.REQUIRED)
        int viewCount,
        @Schema(description = "좋아요수", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        int likeCount,
        @Schema(description = "이웃공개여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean isPrivate,
        @Schema(description = "게시글 생성 시간", example = "2024-12-27T11:11:37", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime createAt,
        @Schema(description = "작성자 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long authorId,
        @Schema(description = "작성자 닉네임", example = "나테스트", requiredMode = Schema.RequiredMode.REQUIRED)
        String author
) {

    @QueryProjection
    public PostDTO {
    }
}
