package lte.backend.auth.dto.request;

public record ReissueTokenRequest(
        String refreshToken
) {
}
