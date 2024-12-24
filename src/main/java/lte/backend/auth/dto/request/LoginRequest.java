package lte.backend.auth.dto.request;

public record LoginRequest(
        String username,
        String password
) {
}
