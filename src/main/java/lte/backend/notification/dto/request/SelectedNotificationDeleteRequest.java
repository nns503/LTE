package lte.backend.notification.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "선택 알림 삭제 요청 데이터")
public record SelectedNotificationDeleteRequest(
        @Schema(description = "선택된 알림 삭제 식별자 리스트", requiredMode = Schema.RequiredMode.REQUIRED)
        List<Long> ids
) {
}
