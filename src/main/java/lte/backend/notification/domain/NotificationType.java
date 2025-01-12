package lte.backend.notification.domain;

public enum NotificationType {

    NEW_FOLLOWER("%s 님이 팔로우를 했습니다."),
    FOLLOWEE_NEW_POST("%s 님이 새로운 게시글을 올렸습니다."),
    NEW_COMMENT("%s 게시글에 새로운 댓글을 달았습니다."),
    ;

    private final String message;

    NotificationType(String message) {
        this.message = message;
    }

    public String getMessage(String parameter) {
        return String.format(message, parameter);
    }
}
