package lte.backend.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "회원가입 요청 데이터")
public record JoinRequest(
        @Schema(description = "아이디", example = "test1234", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "아이디를 입력해주세요")
        @Pattern(regexp = "^[0-9a-zA-Z]*$", message = "영문, 숫자만 사용 가능합니다.")
        @Size(min = 5, max = 10, message = "길이는 5자에서 10자 이내로 작성해주세요")
        String username,

        @Schema(description = "비밀번호", example = "test1234!!", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[~!@#$%^&*+=()_-])(?=.*[0-9]).+$",
                message = "영문, 숫자, 특수문자가 모두 포함되어야 합니다."
        )
        @Size(min = 6, max = 15, message = "길이는 6자에서 15자 이내로 작성해주세요")
        String password,

        @Schema(description = "닉네임", example = "테스트A1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "닉네임을 입력해주세요")
        @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣]*$", message = "한글, 영문, 숫자만 사용 가능합니다.")
        @Size(min = 3, max = 6, message = "길이는 3자에서 6자 이내로 작성해주세요")
        String nickname,

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg", nullable = true)
        String profileUrl
) {
}
