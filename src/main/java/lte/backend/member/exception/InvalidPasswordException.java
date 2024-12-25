package lte.backend.member.exception;

import lte.backend.common.exception.BadRequestLTEException;

public class InvalidPasswordException extends BadRequestLTEException {

    private static final String DEFAULT_MESSAGE = "유효하지 않는 비밀번호입니다.";

    public InvalidPasswordException(String message) {
        super(message);
    }
}
