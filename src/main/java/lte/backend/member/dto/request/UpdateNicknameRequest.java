package lte.backend.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateNicknameRequest(
        @Schema(description = "닉네임", example = "테스트A2", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "닉네임을 입력해주세요")
        @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$", message = "한글, 영문, 숫자만 사용 가능합니다.")
        @Size(min = 3, max = 6, message = "길이는 3자에서 6자 이내로 작성해주세요")
        String nickname
) {
}
