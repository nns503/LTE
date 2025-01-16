package lte.backend.auth.repository;

import lte.backend.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Boolean existsByToken(String token);

    @Transactional
    void deleteByToken(String token);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM RefreshToken rt " +
            "WHERE rt.expiration <= :now")
    void deleteExpiredTokens(LocalDateTime now);
}
