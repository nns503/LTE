package lte.backend.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lte.backend.util.formatter.CustomTimeFormatter;

import java.time.LocalDateTime;

@Schema(description = "댓글 목록에서 사용하는 댓글 정보 데이터")
@NoArgsConstructor
@Data
public class CommentDTO {
    @Schema(description = "댓글 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    Long commentId;
    @Schema(description = "댓글 내용", example = "테스트댓글", requiredMode = Schema.RequiredMode.REQUIRED)
    String content;
    @Schema(description = "댓글 생성 시간", example = "2025-01-01 12:43:31", requiredMode = Schema.RequiredMode.REQUIRED)
    String createAt;
    @Schema(description = "작성자 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    Long authorId;
    @Schema(description = "작성자 이름", example = "나테스트", requiredMode = Schema.RequiredMode.REQUIRED)
    String authorNickname;

    public CommentDTO(Long commentId, String content, LocalDateTime createAt, Long authorId, String authorNickname) {
        this.commentId = commentId;
        this.content = content;
        this.createAt = CustomTimeFormatter.formatToDateTime(createAt);
        this.authorId = authorId;
        this.authorNickname = authorNickname;
    }
}
