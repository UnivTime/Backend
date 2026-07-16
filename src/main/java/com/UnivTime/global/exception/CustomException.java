package com.UnivTime.global.exception;

import likelion.practicespringboot.global.exception.model.BaseErrorCode;
import lombok.Getter;

@Getter
// CustomException이 발생하는 시점은 Runtime!
public class CustomException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public CustomException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
