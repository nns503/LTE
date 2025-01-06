package lte.backend.follow.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "팔로우 상태 데이터")
public record FollowDTO(
        @Schema(description = "회원 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        long memberId,
        @Schema(description = "회원 닉네임", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        String nickname,
        @Schema(description = "팔로우 상태", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean followState
) {
}
