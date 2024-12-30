package lte.backend.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "댓글 등록 요청 데이터")
public record CreateCommentRequest(
        @Schema(description = "내용", example = "테스트댓글내용", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "내용을 입력해주세요")
        @Size(max = 100, message = "댓글은 100자 이내로 작성해주세요")
        String content
) {
}
