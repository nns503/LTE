package lte.backend.follow.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.follow.dto.FollowDTO;
import lte.backend.util.SliceInfo;
import org.springframework.data.domain.Slice;

import java.util.List;

@Schema(description = "팔로위 목록 조회 응답 데이터")
public record GetFolloweeListResponse(
        @Schema(description = "팔로위 목록", requiredMode = Schema.RequiredMode.REQUIRED)
        List<FollowDTO> list,
        @Schema(description = "슬라이스 정보", requiredMode = Schema.RequiredMode.REQUIRED)
        SliceInfo sliceInfo
) {
    public static GetFolloweeListResponse from(Slice<FollowDTO> followerList) {
        return new GetFolloweeListResponse(followerList.getContent(), SliceInfo.from(followerList));
    }
}
