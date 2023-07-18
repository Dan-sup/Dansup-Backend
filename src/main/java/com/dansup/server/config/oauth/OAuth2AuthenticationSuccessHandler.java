package com.dansup.server.config.oauth;

import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.domain.UserRole;
import com.dansup.server.api.user.repository.UserRepository;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import com.dansup.server.config.security.CustomUserDetails;
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

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        log.info("[request URL]: {}", request.getRequestURL());

        CustomUserDetails oAuth2User = (CustomUserDetails) authentication.getPrincipal();

        String targetUrl = null;

        JwtTokenDto jwtTokenDto = tokenProvider.createJwtToken(oAuth2User.getName());
        refreshTokenService.saveRefreshToken(jwtTokenDto.getRefreshToken(), oAuth2User.getName());

        targetUrl = UriComponentsBuilder
                .fromUriString(setSuccessRedirectUrl(request.getServerName(), request.getServerPort()))
                .queryParam("accessToken", jwtTokenDto.getAccessToken())
                .queryParam("refreshToken", jwtTokenDto.getRefreshToken())
                .queryParam("isGuest", isGuest(oAuth2User.getName()))
                .build()
                .toUriString();

        log.info("[targetUrl]: {}", targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    private String setSuccessRedirectUrl(String requestUrl, int requestPort) {
        String redirectUrl = null;

        log.info("[Request URI]: {}, {}", requestUrl, requestPort);

        if(requestUrl.equals("localhost") && requestPort == 8080) {
            redirectUrl = "http://localhost:8080/login/oauth2/success";
        }
        if(requestUrl.equals("localhost") && requestPort == 3000) {
            redirectUrl = "http://localhost:3000/login/oauth2/success";
        }
        if (requestUrl.equals("takgyun.shop")) {
            redirectUrl = "http://localhost:3000/login/oauth2/success";
        }

        return redirectUrl;
    }

    private boolean isGuest(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new BaseException(ResponseCode.USER_NOT_FOUND)
        );

        return user.getUserRole().equals(UserRole.ROLE_GUEST);
    }

}

