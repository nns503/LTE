package lte.backend.follow.exception;

import lte.backend.common.exception.BadRequestLTEException;

public class NonFollowMemberException extends BadRequestLTEException {

    private static final String DEFAULT_MESSAGE = "팔로우를 하지 않은 멤버입니다.";

    public NonFollowMemberException() {
        super(DEFAULT_MESSAGE);
    }
}
