package com.dansup.server.config.jwt;

import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dansup.server.config.jwt.JwtExceptionHandler.handle;

/*
JWT 토큰으로 인증하고 SecurityContextHolder에 추가하는 필터를 가진 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request);
        log.info("[doFilterInternal] token 값 추출 완료: token : {}", token);

        log.info("[doFilterInternal] token 값 유효성 체크 시작");

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("[doFilterInternal] token 값 유효성 체크 완료");
            }
        } catch (BaseException e) {
            log.info("[doFilterInternal] token 값 유효성 체크 실패");
            handle(response, ResponseCode.TOKEN_NOT_VALID);
        }

        filterChain.doFilter(request, response);
    }
}
