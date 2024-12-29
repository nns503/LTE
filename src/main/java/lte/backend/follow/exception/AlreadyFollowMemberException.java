package lte.backend.follow.exception;

import lte.backend.common.exception.DuplicationLTEException;

public class AlreadyFollowMemberException extends DuplicationLTEException {

    private static final String DEFAULT_MESSAGE = "이미 팔로우 중인 멤버입니다.";

    public AlreadyFollowMemberException() {
        super(DEFAULT_MESSAGE);
    }
}
