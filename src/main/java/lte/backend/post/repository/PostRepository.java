package lte.backend.post.repository;

import lte.backend.follow.dto.FollowPostDTO;
import lte.backend.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, GetPostRepository {

    boolean existsById(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.id = :postId")
    void incrementLikeCount(Long postId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Post p SET p.isDeleted = true " +
            "WHERE p.autoDeleted <= :currentTime " +
            "AND p.isDeleted = false ")
    void softDeleteExpiredAutoDeletedPosts(LocalDateTime currentTime);

    @Query("SELECT new lte.backend.follow.dto.FollowPostDTO(p.id, p.title, m.id, m.nickname) " +
            "FROM Post p " +
            "LEFT JOIN Follow f ON  p.member.id = f.followee.id " +
            "LEFT JOIN Member m ON p.member.id = m.id " +
            "WHERE f.follower.id = :memberId " +
            "ORDER BY p.createdAt DESC")
    List<FollowPostDTO> findFolloweePosts(Long memberId, Pageable pageable);
}
