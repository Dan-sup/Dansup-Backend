package com.dansup.server.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BaseException extends Exception {

    private final ExceptionCode exceptionCode;

}
