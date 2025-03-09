package lte.backend.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "프로필 이미지 변경 요청 데이터")
public record UpdateProfileUrlRequest(
        @Schema(description = "프로필 이미지 URL", example = "default_profile_image.jpg")
        String profileUrl
) {
}
