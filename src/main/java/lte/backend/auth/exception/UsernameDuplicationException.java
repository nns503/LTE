package lte.backend.auth.exception;

import lte.backend.common.exception.DuplicationLTEException;

public class UsernameDuplicationException extends DuplicationLTEException {

    private static final String DEFAULT_MESSAGE = "현재 존재하는 아이디입니다.";

    public UsernameDuplicationException() {
        super(DEFAULT_MESSAGE);
    }
}
