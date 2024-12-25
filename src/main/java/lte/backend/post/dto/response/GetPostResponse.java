package lte.backend.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.member.domain.Member;
import lte.backend.post.domain.Post;

import java.time.LocalDateTime;

@Schema(description = "게시글 조회 응답 데이터")
public record GetPostResponse(
        @Schema(description = "제목", example = "테스트제목", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @Schema(description = "내용", example = "테스트내용", requiredMode = Schema.RequiredMode.REQUIRED)
        String content,
        @Schema(description = "조회수", example = "11", requiredMode = Schema.RequiredMode.REQUIRED)
        int viewCount,
        @Schema(description = "추천수", example = "7", requiredMode = Schema.RequiredMode.REQUIRED)
        int likeCount,
        @Schema(description = "이웃공개여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean isPrivate,
        @Schema(description = "자동 삭제 시간, nullable", example = "2024-01-11T11:11:11", nullable = true)
        LocalDateTime autoDeleted,
        @Schema(description = "작성 시간", example = "2024-01-01T11:11:11", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime createdAt,
        @Schema(description = "글 작성자", example = "나테스트", requiredMode = Schema.RequiredMode.REQUIRED)
        String nickname,
        @Schema(description = "글 작성자 식별자", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long memberId
) {

    public static GetPostResponse of(Post post, Member member) {
        return new GetPostResponse(
                post.getTitle(),
                post.getContent(),
                post.getViewCount(),
                post.getLikeCount(),
                post.isPrivate(),
                post.getAutoDeleted(),
                post.getCreatedAt(),
                member.getNickname(),
                member.getId()
        );
    }
}
