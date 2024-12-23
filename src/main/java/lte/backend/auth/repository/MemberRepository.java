package lte.backend.auth.repository;

import lte.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
}
