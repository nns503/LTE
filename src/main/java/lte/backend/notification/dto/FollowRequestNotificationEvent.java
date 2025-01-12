package lte.backend.notification.dto;

import lte.backend.member.domain.Member;

public record FollowRequestNotificationEvent(
        Member sender,
        Member receiver
) {
}
