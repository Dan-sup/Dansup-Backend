package com.dansup.server.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    // 밑에 Response Code 추가

    // success
    SUCCESS_OK(OK, "요청에 성공하였습니다."),
    SUCCESS_CREATED(CREATED, "요청에 성공하였습니다."),

    //fail
    FAIL_BAD_REQUEST(BAD_REQUEST, "잘못된 요청입니다."),
    FAIL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다."),

    // Auth
    SIGNIN_FAILED(UNAUTHORIZED, "로그인에 실패하였습니다."),
    USER_NOT_FOUND(UNAUTHORIZED, "사용자를 찾을 수 없습니다."),
    TOKEN_NOT_VALID(UNAUTHORIZED, "토큰 유효성 검사에 실패하였습니다."),
    ACCESS_DENIED(UNAUTHORIZED, "접근이 금지되었습니다."),

    REFRESH_TOKEN_NOT_FOUND(UNAUTHORIZED, "리프레쉬 토큰이 만료되었습니다"),

    //mypage
    MY_PAGE_NOT_FOUND(NOT_FOUND, "마이페이지 작성이 필요합니다."),

    //profile
    PROFILE_NOT_FOUND(NOT_FOUND, "프로필이 없습니다."),

    //class
    CLASS_NOT_FOUND(NOT_FOUND, "클래스가 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
