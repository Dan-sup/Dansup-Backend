package com.dansup.server.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    // 밑에 Exception Code 추가

    // Auth
    SIGNIN_FAILED(UNAUTHORIZED, "로그인에 실패하였습니다."),
    USER_NOT_FOUND(UNAUTHORIZED, "사용자를 찾을 수 없습니다."),
    TOKEN_NOT_VALID(UNAUTHORIZED, "토큰 유효성 검사에 실패하였습니다."),
    ACCESS_DENIED(UNAUTHORIZED, "접근이 금지되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
