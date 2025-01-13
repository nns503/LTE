package lte.backend.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.member.domain.Member;
import lte.backend.post.domain.Post;

@Schema(description = "신규 댓글 알림 이벤트")
public record NewCommentNotificationEvent(
        @Schema(description = "게시글 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Post post,
        @Schema(description = "수신자 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Member receiver
) {
}
