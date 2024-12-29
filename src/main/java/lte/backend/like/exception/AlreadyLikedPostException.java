package lte.backend.like.exception;

import lte.backend.common.exception.DuplicationLTEException;

public class AlreadyLikedPostException extends DuplicationLTEException {

    private static final String DEFAULT_MESSAGE = "이미 좋아요를 누른 글 입니다";

    public AlreadyLikedPostException() {
        super(DEFAULT_MESSAGE);
    }
}
