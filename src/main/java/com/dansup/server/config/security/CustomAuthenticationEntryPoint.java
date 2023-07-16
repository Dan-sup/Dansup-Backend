package com.dansup.server.config.security;

import com.dansup.server.common.exception.ExceptionCode;
import com.dansup.server.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dansup.server.common.response.Response.setJsonExceptionResponse;

/*
인증이 실패했을 때 예외를 처리하는 클래스
 */

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        log.info("[commence] 인증 실패로 response.sendError 발생");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(setJsonExceptionResponse(ExceptionCode.USER_NOT_FOUND));

        // dto 전송방식이 아니어도 바로 error를 전송하는 방식도 가능함
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);

    }

}
