package lte.backend.notification.exception;

import lte.backend.common.exception.NotFoundLTEException;

public class NotificationNotFoundException extends NotFoundLTEException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 알림입니다.";

    public NotificationNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
