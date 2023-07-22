package com.dansup.server.api.user.service;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.profile.dto.response.GetProfileDetailDto;
import com.dansup.server.api.profile.repository.ProfileRepository;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.api.user.repository.UserRepository;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MyPageService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public GetProfileDetailDto getMyPage(User user) {

        Profile myPage = profileRepository.findByUser(user).orElseThrow(
                () -> new BaseException(ResponseCode.MY_PAGE_NOT_FOUND)
        );

        return GetProfileDetailDto.builder()
                .username(myPage.getUsername())
                .nickname(myPage.getNickname())
                .intro(myPage.getIntro())
                .genres(myPage.getProfileGenres().stream().map(
                        profileGenre -> GenreRequestDto.builder()
                                .genre(profileGenre.getGenre().getName())
                                .build()
                        ).collect(Collectors.toList())
                )
                .hashtag1(myPage.getHashtag1())
                .hashtag2(myPage.getHashtag2())
                .hashtag3(myPage.getHashtag3())
                .build();
    }
}
