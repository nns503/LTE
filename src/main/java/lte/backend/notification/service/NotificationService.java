package lte.backend.notification.service;

import lombok.RequiredArgsConstructor;
import lte.backend.member.domain.Member;
import lte.backend.notification.domain.Notification;
import lte.backend.notification.domain.NotificationType;
import lte.backend.notification.repository.NotificationRepository;
import lte.backend.post.domain.Post;
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

    public void sendFollowRequestNotification(Member sender, Member receiver) {
        sendNotification(NotificationType.NEW_FOLLOWER, sender.getId(), sender.getNickname(), receiver.getId());
    }

    @Transactional
    public void sendNewCommentNotification(Post post, Member receiver) {
        sendNotification(NotificationType.NEW_COMMENT, post.getId(), formatTitle(post.getTitle()), receiver.getId());
    }


    // 알림 목록 <<- 컨트롤러 있음

    // 알림 읽기 << -컨트롤러 있음

    private String formatTitle(String title) {
        if (title.length() > 10) {
            return title.substring(0, 10).trim() + "...";
        }
        return title;
    }
}
