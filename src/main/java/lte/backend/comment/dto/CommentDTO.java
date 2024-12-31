package lte.backend.comment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lte.backend.util.formatter.CustomTimeFormatter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class CommentDTO {
    Long id;
    String content;
    String createAt;
    Long authorId;
    String authorNickname;

    public CommentDTO(Long id, String content, LocalDateTime createAt, Long authorId, String authorNickname) {
        this.id = id;
        this.content = content;
        this.createAt = CustomTimeFormatter.formatToDateTime(createAt);
        this.authorId = authorId;
        this.authorNickname = authorNickname;
    }
}
