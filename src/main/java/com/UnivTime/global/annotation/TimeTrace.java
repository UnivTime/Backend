package com.UnivTime.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메서드 실행 시간을 로그로 출력하는 어노테이션입니다.
 * methodName: 로그에 표시할 메서드명 (미입력 시 실제 메서드명 사용)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeTrace {

    String methodName() default "";
}
