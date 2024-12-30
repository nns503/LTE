package lte.backend.comment.exception;

import lte.backend.common.exception.NotFoundLTEException;

public class CommentNotFoundException extends NotFoundLTEException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
