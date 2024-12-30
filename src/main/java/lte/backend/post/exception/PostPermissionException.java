package lte.backend.post.exception;

import lte.backend.common.exception.ForbiddenLTEException;

public class PostPermissionException extends ForbiddenLTEException {

    private static final String DEFAULT_MESSAGE = "게시글 권한이 없습니다.";

    public PostPermissionException() {
        super(DEFAULT_MESSAGE);
    }
}
