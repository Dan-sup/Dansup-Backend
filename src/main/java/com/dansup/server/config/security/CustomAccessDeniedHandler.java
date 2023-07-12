package com.dansup.server.config.security;

import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
액세스 권한이 없는 리소스에 접근할 경우 발생하는 예외 클래스
 */
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {
        log.info("[handle] 접근이 막혔을 경우 에러 throw");
        throw new BaseException(ExceptionCode.ACCESS_DENIED);
    }

}
