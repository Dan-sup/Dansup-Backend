package com.dansup.server.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    // 밑에 Exception Code 추가

    // Auth
    SIGNIN_FAILED(UNAUTHORIZED, "로그인에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
