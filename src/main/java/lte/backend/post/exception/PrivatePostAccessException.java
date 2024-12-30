package lte.backend.post.exception;

import lte.backend.common.exception.ForbiddenLTEException;

public class PrivatePostAccessException extends ForbiddenLTEException {

    private static final String DEFAULT_MESSAGE = "이웃만 조회할 수 있습니다";

    public PrivatePostAccessException() {
        super(DEFAULT_MESSAGE);
    }
}
