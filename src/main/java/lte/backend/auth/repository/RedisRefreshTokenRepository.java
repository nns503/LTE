package lte.backend.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisRefreshTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "refreshToken:";

    public void save(Long userId, String refreshToken, long ttl) {
        String key = PREFIX + refreshToken;
        redisTemplate.opsForValue().set(key, userId.toString(), ttl, TimeUnit.MILLISECONDS);
    }

    public boolean exists(String refreshToken) {
        return redisTemplate.hasKey(PREFIX + refreshToken);
    }

    public void delete(String refreshToken) {
        redisTemplate.delete(PREFIX + refreshToken);
    }
}
