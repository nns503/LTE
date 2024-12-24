package lte.backend.member.exception;

import lte.backend.common.exception.NotFoundLTEException;

public class MemberNotFoundException extends NotFoundLTEException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 회원입니다.";

    public MemberNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
