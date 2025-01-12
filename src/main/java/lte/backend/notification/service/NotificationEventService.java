package lte.backend.notification.service;

import lombok.RequiredArgsConstructor;
import lte.backend.follow.repository.FollowRepository;
import lte.backend.member.domain.Member;
import lte.backend.notification.domain.NotificationType;
import lte.backend.notification.dto.FollowRequestNotificationEvent;
import lte.backend.notification.dto.FolloweeNewPostNotificationEvent;
import lte.backend.notification.dto.NewCommentNotificationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationEventService {

    private final NotificationService notificationService;

    private final FollowRepository followRepository;

    @TransactionalEventListener
    public void sendFolloweeNewPostNotification(FolloweeNewPostNotificationEvent event) {
        List<Member> followers = followRepository.findFollowersByFolloweeId(event.receiver().getId());
        followers.forEach(follower -> notificationService.sendNotification(
                NotificationType.FOLLOWEE_NEW_POST,
                event.post().getId(),
                follower.getNickname(),
                follower.getId()));
    }

    @TransactionalEventListener
    public void sendFollowRequestNotification(FollowRequestNotificationEvent event) {
        notificationService.sendNotification(
                NotificationType.NEW_FOLLOWER,
                event.sender().getId(),
                event.sender().getNickname(),
                event.receiver().getId());
    }

    @TransactionalEventListener
    public void sendNewCommentNotification(NewCommentNotificationEvent event) {
        notificationService.sendNotification(
                NotificationType.NEW_COMMENT,
                event.post().getId(),
                formatTitle(event.post().getTitle()),
                event.receiver().getId());
    }

    private String formatTitle(String title) {
        if (title.length() > 10) {
            return title.substring(0, 10).trim() + "...";
        }
        return title;
    }
}
