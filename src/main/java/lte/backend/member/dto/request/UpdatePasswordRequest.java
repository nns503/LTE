package lte.backend.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "비밀번호 변경 요청 데이터")
public record UpdatePasswordRequest(
        @Schema(description = "비밀번호", example = "test1234!!", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[~!@#$%^&*+=()_-])(?=.*[0-9]).+$",
                message = "영문, 숫자, 특수문자가 모두 포함되어야 합니다."
        )
        @Size(min = 6, max = 15, message = "길이는 6자에서 15자 이내로 작성해주세요")
        String currentPassword,

        @Schema(description = "비밀번호", example = "test4567!!", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[~!@#$%^&*+=()_-])(?=.*[0-9]).+$",
                message = "영문, 숫자, 특수문자가 모두 포함되어야 합니다."
        )
        @Size(min = 6, max = 15, message = "길이는 6자에서 15자 이내로 작성해주세요")
        String newPassword
) {
}
