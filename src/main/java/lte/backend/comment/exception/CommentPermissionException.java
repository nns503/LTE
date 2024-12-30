package lte.backend.comment.exception;

import lte.backend.common.exception.ForbiddenLTEException;

public class CommentPermissionException extends ForbiddenLTEException {

    private static final String DEFAULT_MESSAGE = "댓글 권한이 없습니다.";

    public CommentPermissionException() {
        super(DEFAULT_MESSAGE);
    }
}
