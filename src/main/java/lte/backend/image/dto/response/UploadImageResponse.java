package lte.backend.image.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이미지 업로드 응답 데이터")
public record UploadImageResponse(
        @Schema(description = "이미지 URL", example = "https://test.cloudfront.net/test_image.jpg", requiredMode = Schema.RequiredMode.REQUIRED)
        String url
) {
}
