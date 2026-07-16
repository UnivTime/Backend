package com.UnivTime.global.exception;

import likelion.practicespringboot.global.common.BaseResponse;
import likelion.practicespringboot.global.exception.model.BaseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
// 컨트롤러에서 발생한 예외 처리
public class GlobalExceptionHandler {

    // 1. 커스텀 예외 처리 핸들러
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();
        log.warn("CustomException 발생: {} - {}", errorCode.getCode(), errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(BaseResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

    // 2. @Valid 유효성 검사(Validation) 실패 처리 핸들러
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> String.format("[%s] %s", e.getField(), e.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        log.warn("Validation 오류 발생: {}", errorMessages);

        return ResponseEntity
                .badRequest()
                .body(BaseResponse.error(
                        GlobalErrorCode.INVALID_INPUT_VALUE.getCode(),
                        GlobalErrorCode.INVALID_INPUT_VALUE.getMessage()
                ));
    }

    // 3. 지원하지 않는 HTTP 메서드 요청 처리 핸들러 (405 Method Not Allowed)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<?>> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.warn("지원하지 않는 HTTP 메서드 요청: {}", ex.getMethod());

        return ResponseEntity
                .status(GlobalErrorCode.METHOD_NOT_ALLOWED.getStatus())
                .body(BaseResponse.error(
                        GlobalErrorCode.METHOD_NOT_ALLOWED.getCode(),
                        GlobalErrorCode.METHOD_NOT_ALLOWED.getMessage()
                ));
    }

    // 4. 잘못된 JSON 형식의 요청 본문 처리 핸들러 (400 Bad Request)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("JSON 파싱 오류 발생: {}", ex.getMessage());

        return ResponseEntity
                .status(GlobalErrorCode.INVALID_JSON_FORMAT.getStatus())
                .body(BaseResponse.error(
                        GlobalErrorCode.INVALID_JSON_FORMAT.getCode(),
                        GlobalErrorCode.INVALID_JSON_FORMAT.getMessage()
                ));
    }

    // 5. 그 외 예상치 못한 모든 최상위 예외 처리 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception ex) {
        log.error("Server 오류 발생: ", ex);

        return ResponseEntity
                .status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(BaseResponse.error(
                        GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                        GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage()
                ));
    }
}
