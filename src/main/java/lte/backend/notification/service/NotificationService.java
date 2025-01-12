package lte.backend.notification.service;

import lombok.RequiredArgsConstructor;
import lte.backend.member.domain.Member;
import lte.backend.notification.domain.Notification;
import lte.backend.notification.domain.NotificationType;
import lte.backend.notification.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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


    // 알림 목록 <<- 컨트롤러 있음

    // 알림 읽기 << -컨트롤러 있음
}
