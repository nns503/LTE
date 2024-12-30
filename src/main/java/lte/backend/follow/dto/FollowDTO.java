package lte.backend.follow.dto;

public record FollowDTO(
        long memberId,
        String nickname,
        boolean followState
) {
}
