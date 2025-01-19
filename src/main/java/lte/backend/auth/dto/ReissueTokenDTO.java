package lte.backend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "재발급된 토큰 데이터")
public record ReissueTokenDTO(
        @Schema(description = "액세스 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
        String accessToken,
        @Schema(description = "리프레쉬 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
        String refreshToken
) {
}
