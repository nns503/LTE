package lte.backend.comment.dto;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        String content,
        LocalDateTime createAt,
        Long authorId,
        String authorNickname
) {
}
