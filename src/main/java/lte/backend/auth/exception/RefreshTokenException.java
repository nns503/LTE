package lte.backend.auth.exception;

import lte.backend.common.exception.UnauthorizedLTEException;

public class RefreshTokenException extends UnauthorizedLTEException {

    private static final String DEFAULT_MESSAGE = "잘못된 재발급 토큰입니다.";

    public RefreshTokenException(String message) {
        super(message);
    }

    public RefreshTokenException() {
        super(DEFAULT_MESSAGE);
    }
}
