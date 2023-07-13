package com.dansup.server.config.jwt;

import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.exception.ExceptionCode;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dansup.server.common.response.Response.setJsonExceptionResponse;

public class JwtExceptionHandler {

    public static void handle(HttpServletResponse response, ExceptionCode exception) throws BaseException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setContentType("application/json");
        response.setStatus(exception.getHttpStatus().value());
        response.getWriter().print(setJsonExceptionResponse(exception));

    }
}
