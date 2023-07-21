package com.dansup.server.api.auth.service;

import com.dansup.server.api.auth.dto.request.RefreshTokenDto;
import com.dansup.server.api.auth.dto.request.SignUpDto;
import com.dansup.server.api.auth.dto.response.AccessTokenDto;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.profile.domain.ProfileImage;
import com.dansup.server.api.profile.domain.ProfileVideo;
import com.dansup.server.api.profile.repository.ProfileImageRepository;
import com.dansup.server.api.profile.repository.ProfileRepository;
import com.dansup.server.api.profile.repository.ProfileVideoRepository;
import com.dansup.server.api.profile.service.PortfolioService;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.domain.UserRole;
import com.dansup.server.api.user.repository.UserRepository;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import com.dansup.server.config.jwt.JwtTokenProvider;
import com.dansup.server.config.jwt.refresh.RefreshToken;
import com.dansup.server.config.jwt.refresh.RefreshTokenRepository;
import com.dansup.server.config.s3.S3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private final S3UploaderService s3UploaderService;

    private final PortfolioService portfolioService;

    private final JwtTokenProvider jwtTokenProvider;

    private final ProfileImageRepository profileImageRepository;
    private final ProfileVideoRepository profileVideoRepository;


    public void signUp(User user, SignUpDto signUpDto, MultipartFile pf_Image, MultipartFile pf_Video) throws IOException {

        String profileImage_url = s3UploaderService.imageUpload(pf_Image);
        ProfileImage profileImage = ProfileImage.builder().url(profileImage_url).build();
        profileImageRepository.save(profileImage);

        String profileVideo_url = s3UploaderService.videoUpload(pf_Video);
        ProfileVideo profileVideo = ProfileVideo.builder().url(profileVideo_url).build();
        profileVideoRepository.save(profileVideo);

        Profile profile = Profile.createProfile(user, signUpDto, profileImage, profileVideo);
        profileRepository.save(profile);

        portfolioService.createPortfolio(profile, signUpDto.getPortfolios());

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
