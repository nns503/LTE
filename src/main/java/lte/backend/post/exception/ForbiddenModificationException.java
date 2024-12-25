package lte.backend.post.exception;

import lte.backend.common.exception.ForbiddenLTEException;

public class ForbiddenModificationException extends ForbiddenLTEException {

    private static final String DEFAULT_MESSAGE = "게시글 수정 권한이 없습니다.";

    public ForbiddenModificationException() {
        super(DEFAULT_MESSAGE);
    }
}
