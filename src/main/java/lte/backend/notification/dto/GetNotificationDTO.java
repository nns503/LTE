package lte.backend.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.notification.domain.Notification;
import lte.backend.util.formatter.CustomTimeFormatter;

@Schema(description = "알림 내용 데이터")
public record GetNotificationDTO(
        @Schema(description = "알림 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        long id,
        @Schema(description = "알림 내용", example = "김테스트 님이 새로운 게시글을 올렸습니다.", requiredMode = Schema.RequiredMode.REQUIRED)
        String content,
        @Schema(description = "알림 타입", example = "FOLLOWEE_NEW_POST", requiredMode = Schema.RequiredMode.REQUIRED)
        String type,
        @Schema(description = "알림 관련 엔티티 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        long relatedId,
        @Schema(description = "알림 읽음 여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean isRead,
        @Schema(description = "알림 생성 시간", example = "2024-12-27 11:11:37", requiredMode = Schema.RequiredMode.REQUIRED)
        String createAt
) {

    public static GetNotificationDTO from(Notification notification) {
        return new GetNotificationDTO(
                notification.getId(),
                notification.getContent(),
                notification.getNotificationType().name(),
                notification.getRelatedId(),
                notification.isRead(),
                CustomTimeFormatter.formatToDateTime(notification.getCreatedAt())
        );
    }
}
