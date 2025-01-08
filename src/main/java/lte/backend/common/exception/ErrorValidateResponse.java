package lte.backend.common.exception;

import java.util.List;

public record ErrorValidateResponse(
        int status,
        List<ErrorDetail> errors
) {

    public record ErrorDetail(
            String field,
            String message
    ) {
    }
}
