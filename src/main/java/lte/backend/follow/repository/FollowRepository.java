package lte.backend.follow.repository;

import lte.backend.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFolloweeIdAndFollowerId(Long FolloweeId, Long FollowerId);

    Optional<Follow> findByFolloweeIdAndFollowerId(Long FolloweeId, Long FollowerId);
}
