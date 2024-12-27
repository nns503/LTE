package lte.backend.util;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Slice;

@Schema(description = "슬라이스 페이징 데이터")
public record SliceInfo(
        @Schema(description = "현재 페이지", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        int page,
        @Schema(description = "요청 조회 페이지 수", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
        int pageSize,
        @Schema(description = "조회된 페이지 수", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        int elements,
        @Schema(description = "다음 페이지 존재 여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
        boolean hasNext
) {
    public static SliceInfo from(Slice<?> slice) {
        return new SliceInfo(slice.getNumber() + 1, slice.getSize(), slice.getNumberOfElements(), slice.hasNext());
    }
}
