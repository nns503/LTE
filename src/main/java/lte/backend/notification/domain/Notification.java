package lte.backend.notification.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lte.backend.common.CreateTimeEntity;
import lte.backend.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "NOTIFICATION")
public class Notification extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private NotificationType notificationType;

    private Long relatedId;

    @NotNull
    @Column(nullable = false)
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Notification(Long id, String content, NotificationType notificationType, Long relatedId, boolean isRead, Member member) {
        this.id = id;
        this.content = content;
        this.notificationType = notificationType;
        this.relatedId = relatedId;
        this.isRead = isRead;
        this.member = member;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
