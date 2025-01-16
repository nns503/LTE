package lte.backend.notification.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.notification.domain.Notification;
import lte.backend.notification.dto.GetNotificationDTO;

import java.util.List;

@Schema(description = "알림 목록 조회 응답 데이터")
public record GetNotificationResponse(
        @Schema(description = "알림 목록", requiredMode = Schema.RequiredMode.REQUIRED)
        List<GetNotificationDTO> notifications
) {
    public static GetNotificationResponse from(List<Notification> notifications) {
        return new GetNotificationResponse(notifications.stream()
                .map(GetNotificationDTO::from)
                .toList());
    }
}
