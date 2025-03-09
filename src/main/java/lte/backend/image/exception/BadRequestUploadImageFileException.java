package lte.backend.image.exception;

import lte.backend.common.exception.BadRequestLTEException;

public class BadRequestUploadImageFileException extends BadRequestLTEException {

    private static final String DEFAULT_MESSAGE = "이미지 파일이 잘못 되었습니다.";

    public BadRequestUploadImageFileException() {
        super(DEFAULT_MESSAGE);
    }
}
