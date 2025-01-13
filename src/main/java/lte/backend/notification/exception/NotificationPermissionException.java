package lte.backend.notification.exception;

import lte.backend.common.exception.ForbiddenLTEException;

public class NotificationPermissionException extends ForbiddenLTEException {

    private static final String DEFAULT_MESSAGE = "알람 접근 권한이 없습니다.";

    public NotificationPermissionException() {
        super(DEFAULT_MESSAGE);
    }
}
