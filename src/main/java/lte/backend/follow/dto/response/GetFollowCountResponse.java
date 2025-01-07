package lte.backend.follow.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "팔로워 및 팔로위 수 응답 데이터")
public record GetFollowCountResponse(
        @Schema(description = "팔로워 수", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
        long followerCount,
        @Schema(description = "팔로위 수", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        long followeeCount
) {
}
