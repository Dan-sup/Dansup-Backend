package com.dansup.server.common.exception;

import com.dansup.server.common.response.Response;
import com.dansup.server.common.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public Response<BaseException> handleBusinessException(BaseException baseException) {
        log.error("[BaseException 발생]: {}", baseException.getMessage());
        log.error("[BaseException 발생]", baseException);
        return Response.fail(baseException.getExceptionCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public Response<BaseException> handleBusinessException(RuntimeException runtimeException) {
        log.error("[RuntimeException 발생]: {}", runtimeException.getMessage());
        log.error("[RuntimeException 발생]", runtimeException);

        return Response.fail(ResponseCode.FAIL_BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public Response<BaseException> handleBusinessException(Exception exception) {
        log.error("[Exception 발생]: {}", exception.getMessage());
        log.error("[Exception 발생]", exception);

        return Response.fail(ResponseCode.FAIL_SERVER_ERROR);
    }

}
