package lte.backend.notification.service;

import lombok.RequiredArgsConstructor;
import lte.backend.member.domain.Member;
import lte.backend.notification.domain.Notification;
import lte.backend.notification.domain.NotificationType;
import lte.backend.notification.dto.request.SelectedNotificationDeleteRequest;
import lte.backend.notification.dto.response.GetNotificationResponse;
import lte.backend.notification.exception.NotificationNotFoundException;
import lte.backend.notification.exception.NotificationPermissionException;
import lte.backend.notification.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Async
    protected void sendNotification(NotificationType notificationType, Long relatedId, String messageParam, long receiveMemberId) {
        Notification notification = Notification.builder()
                .content(notificationType.getMessage(messageParam))
                .notificationType(notificationType)
                .relatedId(relatedId)
                .isRead(false)
                .member(new Member(receiveMemberId))
                .build();
        notificationRepository.save(notification);
    }

    public GetNotificationResponse getNotification(Long memberId) {
        List<Notification> notifications = notificationRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        return GetNotificationResponse.from(notifications);
    }

    @Transactional
    public void readNotification(Long notificationId, Long memberId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(NotificationNotFoundException::new);
        validateMember(memberId, notification);
        notification.setRead(true);
    }

    @Transactional
    public void readNotifications(Long memberId) {
        notificationRepository.findByMemberIdAndIsReadFalse(memberId)
                .forEach(notification -> notification.setRead(true));
    }

    @Transactional
    public void selectedNotificationDelete(SelectedNotificationDeleteRequest request, Long memberId) {
        notificationRepository.deleteByMemberIdAndIdIn(memberId, request.ids());
    }

    @Transactional
    public void readNotificationsDelete(Long memberId) {
        notificationRepository.deleteByMemberIdAndIsReadTrue(memberId);
    }

    private void validateMember(Long memberId, Notification notification) {
        if (!notification.getMember().getId().equals(memberId)) {
            throw new NotificationPermissionException();
        }
    }
}
