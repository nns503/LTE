package lte.backend.notification.dto;

import lte.backend.member.domain.Member;
import lte.backend.post.domain.Post;

public record NewCommentNotificationEvent(
        Post post,
        Member receiver
) {
}
