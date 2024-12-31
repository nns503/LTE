package lte.backend.auth.dto;

public record ReissueTokenDTO(
        String accessToken,
        String refreshToken
) {
}
