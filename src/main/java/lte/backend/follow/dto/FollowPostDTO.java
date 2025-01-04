package lte.backend.follow.dto;

public record FollowPostDTO(
        Long postId,
        String title,
        Long authorId,
        String author
) {
}
