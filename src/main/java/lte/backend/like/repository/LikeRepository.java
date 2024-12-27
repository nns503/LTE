package lte.backend.like.repository;

import lte.backend.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByPostIdAndMemberId(Long postId, Long memberId);
}
