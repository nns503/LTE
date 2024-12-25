package lte.backend.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시글 수정 응답 데이터")
public record UpdatePostResponse(
        @Schema(description = "게시글 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long postId
) {
}
