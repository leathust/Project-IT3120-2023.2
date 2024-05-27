package com.project.library_management.config.application;

import com.project.library_management.config.exception.InvalidArgumentException;
import com.project.library_management.config.exception.ResourceFetchException;
import com.project.library_management.config.exception.ResourceNotFoundException;
import com.project.library_management.payload.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handle404() {
        return buildErrorResponseEntity("Request resource does not exist");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<BaseResponse<Object>> handleIO(Exception e) {
        LOGGER.error("Exception cause by: ", e);
        return buildErrorResponseEntity("An error occurred in IO streams");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BaseResponse<Object>> handleAccessDenied() {
        return buildErrorResponseEntity("Access denied");
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<BaseResponse<Object>> handleInvalidArgumentException(Exception e) {
        return buildErrorResponseEntity(e.getMessage());
    }

    @ExceptionHandler(ResourceFetchException.class)
    public ResponseEntity<BaseResponse<Object>> handleResourceFetchException(Exception e) {
        LOGGER.error("Exception Caused By: ", e);
        return buildErrorResponseEntity(e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleResourceNotFoundException() {
        return buildErrorResponseEntity("Resource not  found");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException e) {
        StringBuilder stringBuilder = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach(error -> stringBuilder.append(String.format("%s : %s ", ((FieldError) error).getField(), error.getDefaultMessage())));
        return buildErrorResponseEntity(stringBuilder.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleError(Exception e) {
        LOGGER.error("Exception Caused By: ", e);
        return buildErrorResponseEntity(e.getClass().getName() + " " + e.getMessage());
    }

    public ResponseEntity<BaseResponse<Object>> buildErrorResponseEntity(String msg) {
        return ResponseEntity.ok(BaseResponse.builder()
                .isError(true)
                .message(msg)
                .build()
        );
    }
}
