package lte.backend.notification.service;

import lombok.RequiredArgsConstructor;
import lte.backend.follow.repository.FollowRepository;
import lte.backend.member.domain.Member;
import lte.backend.notification.domain.NotificationType;
import lte.backend.notification.dto.FolloweeNewPostNotificationEvent;
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
        List<Member> followers = followRepository.findFollowersByFolloweeId(event.member().getId());
        followers.forEach(follower -> notificationService.sendNotification(
                NotificationType.FOLLOWEE_NEW_POST,
                event.post().getId(),
                follower.getNickname(),
                follower.getId()));
    }
}
