package lte.backend.post.exception;

import lte.backend.common.exception.BadRequestLTEException;

public class InvalidDeletedTimeBadRequestException extends BadRequestLTEException {

    private static final String DEFAULT_MESSAGE = "시간 설정이 잘못 되었습니다.";

    public InvalidDeletedTimeBadRequestException(String message) {
        super(message);
    }

    public InvalidDeletedTimeBadRequestException() {
        super(DEFAULT_MESSAGE);
    }
}
