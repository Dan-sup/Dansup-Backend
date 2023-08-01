package com.dansup.server.api.user.service;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.domain.State;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.api.danceclass.repository.DanceClassRepository;
import com.dansup.server.api.profile.domain.PortfolioVideo;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.profile.domain.ProfileVideo;
import com.dansup.server.api.profile.dto.response.GetFileUrlDto;
import com.dansup.server.api.profile.dto.response.GetPortfolioDto;
import com.dansup.server.api.profile.dto.response.GetProfileDetailDto;
import com.dansup.server.api.profile.repository.PortfolioRepository;
import com.dansup.server.api.profile.repository.PortfolioVideoRepository;
import com.dansup.server.api.profile.repository.ProfileRepository;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.repository.UserRepository;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import com.dansup.server.config.s3.S3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MyPageService {

    private final ProfileRepository profileRepository;

    private final DanceClassRepository danceClassRepository;

    private final S3UploaderService s3UploaderService;

    private final PortfolioVideoRepository portfolioVideoRepository;

    public GetProfileDetailDto getMyPage(User user) {

        Profile myPage = loadMyPage(user);

        return GetProfileDetailDto.builder()
                .username(myPage.getUsername())
                .nickname(myPage.getNickname())
                .intro(myPage.getIntro())
                .genres(myPage.getProfileGenres().stream().map(
                        profileGenre -> GenreRequestDto.builder()
                                .genre(
                                        (profileGenre.getGenre() != null) ?
                                        profileGenre.getGenre().getName() : null
                                )
                                .build()
                        ).collect(Collectors.toList())
                )
                .hashtag1(myPage.getHashtag1())
                .hashtag2(myPage.getHashtag2())
                .hashtag3(myPage.getHashtag3())
                .profileImageUrl(myPage.getProfileImage().getUrl())
                .profileVideoUrl(myPage.getProfileVideo().getUrl())
                .build();
    }

    public List<GetPortfolioDto> getPortfolioList(User user) {
        Profile myPage = loadMyPage(user);

        return myPage.getPortfolios().stream().map(
                portfolio -> GetPortfolioDto.builder()
                        .date(portfolio.getDate())
                        .detail(portfolio.getDetail())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<GetFileUrlDto> getPortfolioVideoList(User user) {
        Profile myPage = loadMyPage(user);

        return myPage.getPortfolioVideos().stream().map(
                portfolioVideo -> GetFileUrlDto.builder()
                        .url(portfolioVideo.getUrl())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<GetDanceClassListDto> getDanceClassList(User user) {
        List<DanceClass> danceClasses = danceClassRepository.findByUserAndStateNot(user, State.Delete);

        Profile myPage = loadMyPage(user);

        return danceClasses.stream().map(
                danceClass -> GetDanceClassListDto.builder()
                        .profileId(myPage.getId())
                        .userNickname(myPage.getNickname())
                        .userProfileImage(myPage.getProfileImage().getUrl())
                        .danceClassId(danceClass.getId())
                        .title(danceClass.getTitle())
                        .genres(danceClass.getClassGenres().stream().map(
                                profileGenre -> GenreRequestDto.builder()
                                        .genre(
                                                (profileGenre.getGenre() != null) ?
                                        profileGenre.getGenre().getName() : null
                                        )
                                        .build()
                        ).collect(Collectors.toList()))
                        .location(danceClass.getLocation())
                        .method(danceClass.getMethod().getMethod())
                        .videoUrl(danceClass.getClassVideo().getVideoUrl())
                        .mon(danceClass.isMon())
                        .tue(danceClass.isTue())
                        .wed(danceClass.isWed())
                        .thu(danceClass.isThu())
                        .fri(danceClass.isFri())
                        .sat(danceClass.isSat())
                        .sun(danceClass.isSun())
                        .date(danceClass.getDate())
                        .state(danceClass.getState().toString())
                        .build()
        ).collect(Collectors.toList());
    }

    public void uploadPortfolioVideo(User user, MultipartFile multipartFile) throws IOException {

        Profile myPage = loadMyPage(user);

        String videoUrl = s3UploaderService.videoUpload(multipartFile);
        PortfolioVideo portfolioVideo = PortfolioVideo.builder()
                                                .url(videoUrl)
                                                .profile(myPage)
                                                .build();

        portfolioVideoRepository.save(portfolioVideo);
    }

    private Profile loadMyPage(User user) {

        return profileRepository.findByUser(user).orElseThrow(
                () -> new BaseException(ResponseCode.MY_PAGE_NOT_FOUND)
        );
    }

}
