package lte.backend.follow.exception;

import lte.backend.common.exception.BadRequestLTEException;

public class SelfFollowException extends BadRequestLTEException {

    private static final String DEFAULT_MESSAGE = "자신에게 팔로우를 할 수 없습니다.";

    public SelfFollowException() {
        super(DEFAULT_MESSAGE);
    }
}
