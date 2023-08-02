package com.dansup.server.api.auth.service;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.auth.dto.request.RefreshTokenDto;
import com.dansup.server.api.auth.dto.request.SignUpDto;
import com.dansup.server.api.auth.dto.response.AccessTokenDto;
import com.dansup.server.api.genre.GenreService;
import com.dansup.server.api.genre.domain.ClassGenre;
import com.dansup.server.api.genre.domain.ProfileGenre;
import com.dansup.server.api.genre.respository.GenreRepository;
import com.dansup.server.api.genre.respository.ProfileGenreRepository;
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
import java.util.List;

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

    private final GenreService genreService;


    public void signUp(User user, SignUpDto signUpDto, MultipartFile profileImage, MultipartFile profileVideo) throws IOException {

        User findUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new BaseException(ResponseCode.USER_NOT_FOUND)
        );

        if(profileRepository.existsByUsername(signUpDto.getUsername())) {
            throw new BaseException(ResponseCode.DUPLICATE_NICKNAME);
        }

        log.info("[profileImage]: {}", profileImage);
        log.info("[profileVideo]: {}", profileVideo);

        Profile profile = Profile.builder()
                .user(findUser)
                .profileImage(createProfileImage(profileImage))
                .profileVideo(createProfileVideo(profileVideo))
                .signUpDto(signUpDto)
                .build();

        profileRepository.save(profile);

        genreService.creatProfileGenre(signUpDto, profile);
        portfolioService.createPortfolio(profile, signUpDto.getPortfolios());

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

    public AccessTokenDto reissueAccessToken(RefreshTokenDto refreshTokenDto) {

        if(!jwtTokenProvider.validateToken(refreshTokenDto.getRefreshToken())) {
            throw new BaseException(ResponseCode.TOKEN_NOT_VALID);
        }
        if(!refreshTokenRepository.existsById(refreshTokenDto.getRefreshToken())) {
            throw new BaseException(ResponseCode.REFRESH_TOKEN_NOT_FOUND);
        }

        log.info("[reissueAccessToken] 액세스 토큰 재발급 시작");

        RefreshToken refreshToken = refreshTokenRepository.findById(refreshTokenDto.getRefreshToken()).orElseThrow(
                () -> new BaseException(ResponseCode.REFRESH_TOKEN_NOT_FOUND)
        );

        User user = userRepository.findByEmail(refreshToken.getEmail()).orElseThrow(
                () -> new BaseException(ResponseCode.USER_NOT_FOUND)
        );

        AccessTokenDto accessTokenDto = AccessTokenDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(user.getEmail(), new Date()))
                .build();

        log.info("[reissueAccessToken] 액세스 토큰 재발급 완료: {}", accessTokenDto.getAccessToken());

        return accessTokenDto;

    }

    private boolean validFile(MultipartFile multipartFile) {
        log.info("[valid multipartfile]: {}",!multipartFile.isEmpty());
        return !multipartFile.isEmpty();
    }

    private ProfileImage createProfileImage(MultipartFile profileImage) throws IOException {
        String profileImageUrl = null;
        ProfileImage uploadedProfileImage;

        if(profileImage != null && validFile(profileImage)) {
            log.info("[image url not null]");
            profileImageUrl = s3UploaderService.imageUpload(profileImage);
        }

        uploadedProfileImage = ProfileImage.builder()
                .url(profileImageUrl)
                .build();

        profileImageRepository.save(uploadedProfileImage);
        return uploadedProfileImage;
    }

    private ProfileVideo createProfileVideo(MultipartFile profileVideo) throws IOException {
        String profileVideoUrl = null;
        ProfileVideo uploadedProfileVideo;

        if(profileVideo != null && validFile(profileVideo)) {
            log.info("[video url not null]");
            profileVideoUrl = s3UploaderService.videoUpload(profileVideo);
        }

        uploadedProfileVideo = ProfileVideo.builder()
                .url(profileVideoUrl)
                .build();

        profileVideoRepository.save(uploadedProfileVideo);
        return uploadedProfileVideo;
    }

}