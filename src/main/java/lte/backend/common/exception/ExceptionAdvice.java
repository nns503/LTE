package lte.backend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DefaultLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(DefaultLTEException e) {
        return new ResponseEntity<>(
                new ErrorCode(500, e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(BadRequestLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(BadRequestLTEException e) {
        return new ResponseEntity<>(
                new ErrorCode(400, e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UnauthorizedLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(UnauthorizedLTEException e){
        return new ResponseEntity<>(
                new ErrorCode(401, e.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ForbiddenLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(ForbiddenLTEException e){
        return new ResponseEntity<>(
                new ErrorCode(403, e.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(NotFoundLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(NotFoundLTEException e){
        return new ResponseEntity<>(
                new ErrorCode(404, e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicationLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(DuplicationLTEException e){
        return new ResponseEntity<>(
                new ErrorCode(409, e.getMessage()),
                HttpStatus.CONFLICT
        );
    }
}
