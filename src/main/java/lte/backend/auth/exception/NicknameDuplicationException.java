package lte.backend.auth.exception;

import lte.backend.common.exception.DuplicationLTEException;

public class NicknameDuplicationException extends DuplicationLTEException {

    private static final String DEFAULT_MESSAGE = "현재 존재하는 닉네임입니다.";

    public NicknameDuplicationException() {
        super(DEFAULT_MESSAGE);
    }

    public NicknameDuplicationException(String message) {
        super(message);
    }
}
