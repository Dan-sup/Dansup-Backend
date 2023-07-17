package com.dansup.server.api.auth.service;

import com.dansup.server.api.auth.dto.request.RefreshTokenDto;
import com.dansup.server.api.auth.dto.request.SignUpDto;
import com.dansup.server.api.auth.dto.response.AccessTokenDto;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.profile.repository.ProfileRepository;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.domain.UserRole;
import com.dansup.server.api.user.repository.UserRepository;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import com.dansup.server.config.jwt.JwtTokenProvider;
import com.dansup.server.config.jwt.refresh.RefreshToken;
import com.dansup.server.config.jwt.refresh.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;


    public void signUp(User user, SignUpDto signUpDto) {
        // TO DO: 파일 업로드 로직 추가해야함
        Profile profile = Profile.createProfile(user, signUpDto);
        profileRepository.save(profile);

        User findUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new BaseException(ResponseCode.USER_NOT_FOUND)
        );
        findUser.updateUserRole(UserRole.ROLE_USER);

        log.info("[Sign Up 완료]: {}, {}", profile.getId(), findUser.getUserRole());
    }

    public void signOut(User user, RefreshTokenDto refreshTokenDto) {
        RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenDto.getRefreshToken()).orElseThrow(
                () -> new BaseException(ResponseCode.REFRESH_TOKEN_NOT_FOUND)
        );

        refreshTokenRepository.delete(refreshToken);
        SecurityContextHolder.clearContext();
    }

    public AccessTokenDto reissueAccessToken(User user, RefreshTokenDto refreshTokenDto) {

        if(!jwtTokenProvider.validateToken(refreshTokenDto.getRefreshToken())) {
            throw new BaseException(ResponseCode.TOKEN_NOT_VALID);
        }
        if(!refreshTokenRepository.existsById(refreshTokenDto.getRefreshToken())) {
            throw new BaseException(ResponseCode.REFRESH_TOKEN_NOT_FOUND);
        }

        log.info("[reissueAccessToken] 액세스 토큰 재발급 시작");

        AccessTokenDto accessTokenDto = AccessTokenDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(user.getEmail(), new Date()))
                .build();

        log.info("[reissueAccessToken] 액세스 토큰 재발급 완료: {}", accessTokenDto.getAccessToken());

        return accessTokenDto;

    }
}
