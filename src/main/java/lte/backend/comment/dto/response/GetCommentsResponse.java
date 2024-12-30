package lte.backend.comment.dto.response;

import lte.backend.comment.dto.CommentDTO;
import lte.backend.util.SliceInfo;
import org.springframework.data.domain.Slice;

import java.util.List;

public record GetCommentsResponse(
        List<CommentDTO> comments,
        SliceInfo sliceInfo
) {
    public static GetCommentsResponse from(Slice<CommentDTO> commentList) {
        return new GetCommentsResponse(commentList.getContent(), SliceInfo.from(commentList));
    }
}
