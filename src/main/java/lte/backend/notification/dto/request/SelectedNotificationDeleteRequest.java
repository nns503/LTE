package lte.backend.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "선택 알림 삭제 요청 데이터")
public record SelectedNotificationDeleteRequest(
        List<Long> ids
) {
}
