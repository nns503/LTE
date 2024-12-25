package lte.backend.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "게시글 등록 요청 데이터")
public record CreatePostRequest(
        @Schema(description = "제목", example = "테스트제목", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "제목을 입력해주세요")
        @Size(max = 30, message = "제목은 30자 이내로 작성해주세요")
        String title,
        @Schema(description = "내용", example = "테스트내용", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotEmpty(message = "내용을 입력해주세요")
        String content,
        @Schema(description = "이웃공개여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean isPrivate,
        @Schema(description = "자동 삭제 시간 (nullable)", example = "2025-11-11T11:11:00", nullable = true)
        LocalDateTime autoDeleted
) {
}
