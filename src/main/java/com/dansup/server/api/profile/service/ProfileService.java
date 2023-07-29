package com.dansup.server.api.profile.service;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.domain.State;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.api.danceclass.repository.DanceClassRepository;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.profile.dto.response.GetFileUrlDto;
import com.dansup.server.api.profile.dto.response.GetPortfolioDto;
import com.dansup.server.api.profile.dto.response.GetProfileDetailDto;
import com.dansup.server.api.profile.dto.response.GetProfileListDto;
import com.dansup.server.api.profile.repository.ProfileRepository;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final DanceClassRepository danceClassRepository;


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
                        .url(portfolioVideo.getUrl())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<GetDanceClassListDto> getDanceClassList(User user, Long profileId) {

        Profile profile = loadProfile(profileId);
        List<DanceClass> danceClasses;

        if(profile.getUser().getId().equals(user.getId())) {
            danceClasses = danceClassRepository.findByUserAndStateNot(profile.getUser(), State.Delete);
            log.info("profile_id : {}, p_user_id : {}, user_id : {}", profile.getId(), profile.getUser().getId(), user.getId());
        }
        else {
            danceClasses = danceClassRepository.findByState(State.Active);
            log.info("에러");
        }

        return danceClasses.stream().map(
                danceClass -> GetDanceClassListDto.builder()
                        .userId(profile.getUser().getId())
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
}
