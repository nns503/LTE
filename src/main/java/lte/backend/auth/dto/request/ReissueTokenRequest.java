package lte.backend.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리프레쉬 토큰 재발급 요청 데이터")
public record ReissueTokenRequest(
        @Schema(description = "리프레쉬 토큰", requiredMode = Schema.RequiredMode.REQUIRED)
        String refreshToken
) {
}
