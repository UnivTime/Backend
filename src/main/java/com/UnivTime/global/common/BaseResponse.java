package com.UnivTime.global.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(title = "BaseResponse DTO", description = "공통 API 응답 형식")
public class BaseResponse<T> {

    @Schema(description = "요청 성공 여부", example = "true")
    private boolean success;

    @Schema(description = "HTTP 상태 코드", example = "200")
    private Object code;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    private String message;

    @Schema(description = "응답 데이터")
    private T data;

    // 성공 응답 - 데이터만 넘기는 경우
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(true, 200, "요청이 성공적으로 처리되었습니다.", data);
    }

    // 성공 응답 - 메시지와 데이터를 넘기는 경우
    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(true, 200, message, data);
    }

    // 성공 응답 - 상태 코드, 메시지, 데이터를 모두 넘기는 경우
    public static <T> BaseResponse<T> success(int code, String message, T data) {
        return new BaseResponse<>(true, code, message, data);
    }

    // 에러 응답
    public static <T> BaseResponse<T> error(String code, String message) {
        return new BaseResponse<>(false, code, message, null);
    }
}
