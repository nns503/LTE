package lte.backend.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lte.backend.auth.domain.AuthMember;
import lte.backend.notification.dto.request.SelectedNotificationDeleteRequest;
import lte.backend.notification.dto.response.GetNotificationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/notification")
@Tag(name = "NOTIFICATION", description = "알림 로직 관리")
public interface NotificationApi {

    @Operation(summary = "알림 목록 조회")
    @GetMapping
    ResponseEntity<GetNotificationResponse> getNotification(
            AuthMember member
    );

    @Operation(summary = "단건 알림 읽음 표시")
    @PutMapping("/{notificationId}")
    ResponseEntity<Void> readNotification(
            AuthMember authMember,
            Long notificationId
    );

    @Operation(summary = "전체 알림 읽음 표시")
    @PutMapping
    ResponseEntity<Void> readNotifications(
            AuthMember authMember
    );

    @Operation(summary = "선택된 알림 삭제")
    @DeleteMapping
    ResponseEntity<Void> selectedNotificationDelete(
            SelectedNotificationDeleteRequest request,
            AuthMember authMember
    );

    @Operation(summary = "읽은 알림 전체 삭제")
    @DeleteMapping("/all")
    ResponseEntity<Void> readNotificationsDelete(
            AuthMember authMember
    );
}
