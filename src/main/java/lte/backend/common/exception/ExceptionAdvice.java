package lte.backend.common.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DefaultLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(DefaultLTEException e) {
        return new ResponseEntity<>(
                new ErrorCode(INTERNAL_SERVER_ERROR.value(), e.getMessage()),
                INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(BadRequestLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(BadRequestLTEException e) {
        return new ResponseEntity<>(
                new ErrorCode(BAD_REQUEST.value(), e.getMessage()),
                BAD_REQUEST
        );
    }

    @ExceptionHandler(UnauthorizedLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(UnauthorizedLTEException e) {
        return new ResponseEntity<>(
                new ErrorCode(UNAUTHORIZED.value(), e.getMessage()),
                UNAUTHORIZED
        );
    }

    @ExceptionHandler(ForbiddenLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(ForbiddenLTEException e) {
        return new ResponseEntity<>(
                new ErrorCode(FORBIDDEN.value(), e.getMessage()),
                FORBIDDEN
        );
    }

    @ExceptionHandler(NotFoundLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(NotFoundLTEException e) {
        return new ResponseEntity<>(
                new ErrorCode(NOT_FOUND.value(), e.getMessage()),
                NOT_FOUND
        );
    }

    @ExceptionHandler(DuplicationLTEException.class)
    public ResponseEntity<ErrorCode> handleDefaultException(DuplicationLTEException e) {
        return new ResponseEntity<>(
                new ErrorCode(CONFLICT.value(), e.getMessage()),
                CONFLICT
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<ErrorValidateResponse.ErrorDetail> errorDetails = ex.getBindingResult().getAllErrors().stream()
                .map(error -> new ErrorValidateResponse.ErrorDetail(
                        (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName(),
                        error.getDefaultMessage()
                ))
                .toList();

        return new ResponseEntity<>(new ErrorValidateResponse(BAD_REQUEST.value(), errorDetails), BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorValidateResponse> handleConstraintViolationException(
            ConstraintViolationException ex
    ) {
        List<ErrorValidateResponse.ErrorDetail> errorDetails = ex.getConstraintViolations().stream()
                .map(violation -> new ErrorValidateResponse.ErrorDetail(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                ))
                .toList();

        return new ResponseEntity<>(new ErrorValidateResponse(BAD_REQUEST.value(), errorDetails), BAD_REQUEST);
    }
}