package lte.backend.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.member.domain.Member;

@Schema(description = "신규 팔로워 추가 이벤트")
public record FollowRequestNotificationEvent(
        @Schema(description = "팔로워 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Member sender,
        @Schema(description = "수신자 ID", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        Member receiver
) {
}
