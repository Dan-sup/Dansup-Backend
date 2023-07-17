package com.dansup.server.config.oauth;

import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.repository.UserRepository;
import com.dansup.server.api.user.service.UserService;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import com.dansup.server.config.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final UserService userService;

    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");

        log.info("[카카오 유저 정보] email: {}", email);

        User user;
        if(userRepository.existsByEmail(email)) {
            user = userRepository.findByEmail(email).orElseThrow(
                    () -> new BaseException(ResponseCode.USER_NOT_FOUND)
            );
            log.info("[카카오 유저 등록 확인] user_id: {}", user.getId());

        } else {
            user = userService.createOAuthUser(email);
            log.info("[카카오 유저 등록] user_id: {}", user.getId());
        }

        return new CustomUserDetails(user, attributes);
    }

}
