package lte.backend.post.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lte.backend.common.BaseTimeEntity;
import lte.backend.member.domain.Member;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE posts SET is_deleted = true WHERE id = ?")
@Entity
@Table(name = "POSTS")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false)
    private String content;

    @NotNull
    @Column(nullable = false)
    private int viewCount;

    @NotNull
    @Column(nullable = false)
    private int likeCount;

    @NotNull
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @NotNull
    @Column(name = "is_private", nullable = false)
    private boolean isPrivate;

    private LocalDateTime autoDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Post(Long id, String title, String content, int viewCount, int likeCount, boolean isDeleted, boolean isPrivate, LocalDateTime autoDeleted, Member member) {
        this.id = id;
        this.title = title;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.content = content;
        this.isDeleted = isDeleted;
        this.isPrivate = isPrivate;
        this.autoDeleted = autoDeleted;
        this.member = member;
    }

    public Post(Long id) {
        this.id = id;
    }

    public void update(String title, String content, boolean isPrivate, LocalDateTime autoDeleted) {
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
        this.autoDeleted = autoDeleted;
    }

    public void incrementViewCount() {
        this.viewCount++;
    }
}
