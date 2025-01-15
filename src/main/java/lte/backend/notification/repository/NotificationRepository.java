package lte.backend.notification.repository;

import lte.backend.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByMemberIdOrderByCreatedAtDesc(long memberId);

    List<Notification> findByMemberIdAndIsReadFalse(long memberId);

    void deleteByMemberIdAndIdIn(Long memberId, List<Long> ids);

    void deleteByMemberIdAndIsReadTrue(Long memberId);

    boolean existsByMemberId(long memberId);
}
