package lte.backend.post.exception;

import lte.backend.common.exception.NotFoundLTEException;

public class PostNotFoundException extends NotFoundLTEException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
