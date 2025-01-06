package lte.backend.comment.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.comment.dto.CommentDTO;
import lte.backend.util.SliceInfo;
import org.springframework.data.domain.Slice;

import java.util.List;

@Schema(description = "댓글 목록 조회 응답 데이터")
public record GetCommentsResponse(
        @Schema(description = "조회 댓글 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
        List<CommentDTO> comments,
        @Schema(description = "슬라이스 페이징 정보", requiredMode = Schema.RequiredMode.REQUIRED)
        SliceInfo sliceInfo
) {
    public static GetCommentsResponse from(Slice<CommentDTO> commentList) {
        return new GetCommentsResponse(commentList.getContent(), SliceInfo.from(commentList));
    }
}
