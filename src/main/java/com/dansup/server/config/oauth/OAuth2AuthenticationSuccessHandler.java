package com.dansup.server.config.oauth;


import com.dansup.server.config.jwt.CustomUserDetails;
import com.dansup.server.config.jwt.JwtTokenProvider;
import com.dansup.server.config.jwt.dto.JwtTokenDto;
import com.dansup.server.config.jwt.refresh.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomUserDetails oAuth2User = (CustomUserDetails) authentication.getPrincipal();

        JwtTokenDto jwtTokenDto = tokenProvider.createJwtToken(oAuth2User.getName());

        refreshTokenService.saveRefreshToken(jwtTokenDto.getRefreshToken(), oAuth2User.getName());

        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8080/oauth/kakao/success")
                .queryParam("accessToken", jwtTokenDto.getAccessToken())
                .queryParam("refreshToken", jwtTokenDto.getRefreshToken())
                .build()
                .toUriString();

        log.info("[targetUrl]: {}", targetUrl);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }
    
}

