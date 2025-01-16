package lte.backend.notification.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.domain.AuthMember;
import lte.backend.notification.dto.request.SelectedNotificationDeleteRequest;
import lte.backend.notification.dto.response.GetNotificationResponse;
import lte.backend.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notification")
public class NotificationController implements NotificationApi {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<GetNotificationResponse> getNotifications(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        return ResponseEntity.ok(notificationService.getNotifications(authMember.getMemberId()));
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<Void> readNotification(
            @AuthenticationPrincipal AuthMember authMember,
            @PathVariable Long notificationId
    ) {
        notificationService.readNotification(notificationId, authMember.getMemberId());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> readNotifications(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        notificationService.readNotifications(authMember.getMemberId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> selectedNotificationDelete(
            @RequestBody SelectedNotificationDeleteRequest request,
            @AuthenticationPrincipal AuthMember authMember
    ) {
        notificationService.selectedNotificationDelete(request, authMember.getMemberId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/read")
    public ResponseEntity<Void> readNotificationsDelete(
            @AuthenticationPrincipal AuthMember authMember
    ) {
        notificationService.readNotificationsDelete(authMember.getMemberId());
        return ResponseEntity.ok().build();
    }
}
