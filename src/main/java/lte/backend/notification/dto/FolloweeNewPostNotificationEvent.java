package lte.backend.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.member.domain.Member;
import lte.backend.post.domain.Post;

@Schema(description = "팔로위 새로운 게시글 알림 이벤트")
public record FolloweeNewPostNotificationEvent(
        @Schema(description = "게시글 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Post post,
        @Schema(description = "팔로위 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Member member
) {
}
