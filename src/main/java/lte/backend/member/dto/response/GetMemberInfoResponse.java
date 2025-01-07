package lte.backend.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lte.backend.member.domain.Member;

@Schema(description = "회원 정보 응답 데이터")
public record GetMemberInfoResponse(
        @Schema(description = "회원 닉네임", example = "나테스트", requiredMode = Schema.RequiredMode.REQUIRED)
        String nickname,
        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg", nullable = true)
        String profileUrl
) {
    public static GetMemberInfoResponse from(Member member) {
        return new GetMemberInfoResponse(member.getNickname(), member.getProfileUrl());
    }
}
