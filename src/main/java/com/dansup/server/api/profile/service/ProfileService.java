package com.dansup.server.api.profile.service;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.domain.State;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.api.danceclass.repository.DanceClassRepository;
import com.dansup.server.api.danceclass.service.DanceClassService;
import com.dansup.server.api.profile.domain.PortfolioVideo;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.profile.dto.response.GetFileUrlDto;
import com.dansup.server.api.profile.dto.response.GetPortfolioDto;
import com.dansup.server.api.profile.dto.response.GetProfileDetailDto;
import com.dansup.server.api.profile.dto.response.GetProfileListDto;
import com.dansup.server.api.profile.repository.ProfileRepository;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.repository.UserRepository;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import com.dansup.server.config.s3.S3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dansup.server.common.response.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final DanceClassRepository danceClassRepository;
    private final DanceClassService danceClassService;


    public GetProfileDetailDto getProfileDetail(Long profileId) {

        Profile profile = loadProfile(profileId);

        return GetProfileDetailDto.builder()
                .username(profile.getUsername())
                .nickname(profile.getNickname())
                .intro(profile.getIntro())
                .genres(profile.getProfileGenres().stream().map(
                        profileGenre -> GenreRequestDto.builder()
                                .genre(
                                        (profileGenre.getGenre() != null) ?
                                        profileGenre.getGenre().getName() : null
                                )
                                .build()
                        ).collect(Collectors.toList())
                )
                .hashtag1(profile.getHashtag1())
                .hashtag2(profile.getHashtag2())
                .hashtag3(profile.getHashtag3())
                .profileImageUrl(profile.getProfileImage().getUrl())
                .profileVideoUrl(profile.getProfileVideo().getUrl())
                .build();
    }

    public List<GetProfileListDto> getProfileList(String nickname) {

        List<Profile> profiles = profileRepository.findByNickname(nickname);

        return profiles.stream().map(
                profile -> GetProfileListDto.builder()
                        .profileId(profile.getId())
                        .nickname(profile.getNickname())
                        .intro(profile.getIntro())
                        .genres(profile.getProfileGenres().stream().map(
                                profileGenre -> GenreRequestDto.builder()
                                        .genre(
                                                (profileGenre.getGenre() != null) ?
                                                profileGenre.getGenre().getName() : null
                                        )
                                        .build()
                        ).collect(Collectors.toList()))
                        .hashtag1(profile.getHashtag1())
                        .hashtag2(profile.getHashtag2())
                        .hashtag3(profile.getHashtag3())
                        .profileImage(profile.getProfileImage().getUrl())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<GetPortfolioDto> getPortfolioList(Long profileId) {
        Profile profile = loadProfile(profileId);

        return profile.getPortfolios().stream().map(
                portfolio -> GetPortfolioDto.builder()
                        .date(portfolio.getDate())
                        .detail(portfolio.getDetail())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<GetFileUrlDto> getPortfolioVideoList(Long profileId) {
        Profile profile = loadProfile(profileId);

        return profile.getPortfolioVideos().stream().map(
                portfolioVideo -> GetFileUrlDto.builder()
                        .pvId(portfolioVideo.getId())
                        .url(portfolioVideo.getUrl())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<GetDanceClassListDto> getProfileClassList(Long profileId) {

        Profile profile = loadProfile(profileId);
        User user = profile.getUser();
        List<DanceClass> danceClasses = danceClassRepository.findByUserAndState(user, State.Active);

        return danceClasses.stream().map(
                danceClass -> GetDanceClassListDto.builder()
                        .profileId(profileId)
                        .userNickname(profile.getNickname())
                        .userProfileImage(profile.getProfileImage().getUrl())
                        .danceClassId(danceClass.getId())
                        .title(danceClass.getTitle())
                        .genres(danceClass.getClassGenres().stream().map(
                                classGenre -> GenreRequestDto.builder()
                                        .genre(
                                                (classGenre.getGenre() != null) ?
                                                classGenre.getGenre().getName() : null
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

    private Profile loadProfile(Long profileId) {
        return profileRepository.findById(profileId).orElseThrow(
                () -> new BaseException(ResponseCode.PROFILE_NOT_FOUND)
        );
    }

    private Profile loadProfile(User user) {
        return profileRepository.findByUser(user).orElseThrow(
                () -> new BaseException(ResponseCode.PROFILE_NOT_FOUND)
        );
    }

    public void compareUser(Profile profile, User user) {

        if(!profile.getUser().getId().equals(user.getId())){
            throw new BaseException(FAIL_NOT_POSTER);
        }
    }

    public void deleteProfile(User user) throws BaseException {

        Profile profile = loadProfile(user);

        compareUser(profile, user);

        danceClassService.deleteFile(profile.getProfileImage().getUrl());
        danceClassService.deleteFile(profile.getProfileVideo().getUrl());

        List<PortfolioVideo> portfolioVideos= profile.getPortfolioVideos();

        for(PortfolioVideo portfolioVideo: portfolioVideos){
            danceClassService.deleteFile(portfolioVideo.getUrl());
        }

        profileRepository.delete(profile);
    }
}
