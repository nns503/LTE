package lte.backend.post.repository;

import lte.backend.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long>, GetPostRepository {

    boolean existsById(Long id);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.id = :postId")
    void incrementLikeCount(Long postId);

    @Modifying
    @Query("UPDATE Post p SET p.isDeleted = true " +
            "WHERE p.autoDeleted <= :currentTime " +
            "AND p.isDeleted = false ")
    void softDeleteExpiredAutoDeletedPosts(LocalDateTime currentTime);
}
