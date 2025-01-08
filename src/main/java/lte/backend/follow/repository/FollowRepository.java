package lte.backend.follow.repository;

import lte.backend.follow.domain.Follow;
import lte.backend.follow.dto.FollowDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsByFolloweeIdAndFollowerId(Long FolloweeId, Long FollowerId);
    Optional<Follow> findByFolloweeIdAndFollowerId(Long FolloweeId, Long FollowerId);

    @Query(value = "SELECT new lte.backend.follow.dto.FollowDTO(f1.follower.id, f1.follower.nickname, " +
            "CASE WHEN f2.id IS NOT NULL THEN true ELSE false END) " +
            "FROM Follow f1 " +
            "LEFT JOIN Follow f2 " +
            "ON f1.follower.id = f2.followee.id AND f2.follower.id = :memberId " +
            "WHERE f1.followee.id  = :findMemberId")
    Slice<FollowDTO> findFollowerList(Long memberId, Long findMemberId, Pageable pageable);

    @Query(value = "SELECT new lte.backend.follow.dto.FollowDTO(f1.followee.id, f1.followee.nickname, " +
            "CASE WHEN f2.id IS NOT NULL THEN true ELSE false END) " +
            "FROM Follow f1 " +
            "LEFT JOIN Follow f2 " +
            "ON f1.followee.id = f2.followee.id AND f2.follower.id = :memberId " +
            "WHERE f1.follower.id = :findMemberId")
    Slice<FollowDTO> findFolloweeList(Long memberId, Long findMemberId, Pageable pageable);

    default Long getFollowerCount(Long memberId) {
        return countByFolloweeId(memberId);
    }

    Long countByFolloweeId(Long FolloweeId);

    default Long getFolloweeCount(Long memberId) {
        return countByFollowerId(memberId);
    }

    Long countByFollowerId(Long FollowerId);

    void deleteByFolloweeIdOrFollowerId(Long FolloweeId, Long FollowerId);
}
