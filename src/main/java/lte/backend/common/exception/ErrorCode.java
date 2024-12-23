package lte.backend.common.exception;

public record ErrorCode(
        int status,
        String message
) {
}
