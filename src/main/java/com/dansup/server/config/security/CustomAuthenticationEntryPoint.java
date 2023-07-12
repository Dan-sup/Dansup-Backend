package com.dansup.server.config.security;

import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.exception.ExceptionCode;
import com.dansup.server.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
인증이 실패했을 때 예외를 처리하는 클래스
 */

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        //esponse.Response.fail(ExceptionCode.USER_NOT_FOUND);
    }

}
