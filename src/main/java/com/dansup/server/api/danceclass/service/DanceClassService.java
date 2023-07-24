package com.dansup.server.api.danceclass.service;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.auth.dto.request.HashtagRequestDto;
import com.dansup.server.api.danceclass.domain.ClassVideo;
import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.domain.State;
import com.dansup.server.api.danceclass.dto.request.CreateDanceClassDto;
import com.dansup.server.api.danceclass.dto.response.DayResponseDto;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassDto;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.api.danceclass.repository.ClassVideoRepository;
import com.dansup.server.api.danceclass.repository.DanceClassRepository;
import com.dansup.server.api.genre.GenreService;
import com.dansup.server.api.genre.domain.ClassGenre;
import com.dansup.server.api.genre.respository.ClassGenreRepository;
import com.dansup.server.api.genre.respository.GenreRepository;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.profile.repository.ProfileRepository;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.common.exception.BaseException;
import com.dansup.server.common.response.ResponseCode;
import com.dansup.server.config.s3.S3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dansup.server.common.response.ResponseCode.FAIL_BAD_REQUEST;

@Service
@RequiredArgsConstructor
@Slf4j
public class DanceClassService {

    private final DanceClassRepository danceClassRepository;
    private final ClassVideoRepository classVideoRepository;

    private final ProfileRepository profileRepository;
    private final S3UploaderService s3UploaderService;

    private final GenreService genreService;

    public void createClass(User user, CreateDanceClassDto createDanceClassDto, MultipartFile videofile, MultipartFile thumbnail) throws IOException {

        log.info("[createClass 시작]");
        String videoUrl = s3UploaderService.videoUpload(videofile);
        String thumbnailUrl = s3UploaderService.imageUpload(thumbnail);
        ClassVideo classVideo = ClassVideo.builder().videoUrl(videoUrl).thumbnailUrl(thumbnailUrl).build();
        ClassVideo saved = classVideoRepository.save(classVideo);

        log.info("[classVideo 생성 완료]: exist_ClassVideo_Id = {}, saved_ClassVideo_Id = {}, V_URL = {}, T_URL = {}", classVideo.getId(), saved.getId(), videoUrl, thumbnailUrl);

        DanceClass danceClass = DanceClass.builder().user(user)
                .classVideo(classVideo)
                .title(createDanceClassDto.getTitle())
                .hashtag1(createDanceClassDto.getHashtag1())
                .hashtag2(createDanceClassDto.getHashtag2())
                .hashtag3(createDanceClassDto.getHashtag3())
                .location(createDanceClassDto.getLocation())
                .difficulty(createDanceClassDto.getDifficulty())
                .tuition(createDanceClassDto.getTuition())
                .maxPeople(createDanceClassDto.getMaxPeople())
                .song(createDanceClassDto.getSong())
                .detail1(createDanceClassDto.getDetail1())
                .detail2(createDanceClassDto.getDetail2())
                .detail3(createDanceClassDto.getDetail3())
                .method(createDanceClassDto.getMethod())
                .mon(createDanceClassDto.getDays().isMon())
                .tue(createDanceClassDto.getDays().isTue())
                .wed(createDanceClassDto.getDays().isWed())
                .thu(createDanceClassDto.getDays().isThu())
                .fri(createDanceClassDto.getDays().isFri())
                .sat(createDanceClassDto.getDays().isSat())
                .sun(createDanceClassDto.getDays().isSun())
                .startTime(createDanceClassDto.getStartTime())
                .endTime(createDanceClassDto.getEndTime())
                .date(createDanceClassDto.getDate())
                .reserveLink(createDanceClassDto.getReserveLink())
                .state(State.Active)
                .build();

        danceClassRepository.save(danceClass);

        genreService.creatClassGenre(createDanceClassDto, danceClass);

        log.info("[DanceClass 생성 완료]: DanceClass_title = {}", danceClass.getTitle());
    }

    public List<GetDanceClassListDto> getAllClassList(User user){

        List<DanceClass> classList = danceClassRepository.findByState(State.Active);
        List<GetDanceClassListDto> danceClassListDto = new ArrayList<>();

        Profile profile;

        for(DanceClass danceClass : classList){

            profile = profileRepository.findByUser(danceClass.getUser()).orElseThrow(
                    () -> new BaseException(ResponseCode.PROFILE_NOT_FOUND)
            );

            danceClassListDto.add(GetDanceClassListDto.builder()
                    .userId(danceClass.getUser().getId())
                    .userNickname(profile.getNickname())
                    .userProfileImage(profile.getProfileImage().getUrl())
                    .danceClassId(danceClass.getId())
                    .title(danceClass.getTitle())
                    .genres(danceClass.getClassGenres().stream().map(
                            profileGenre -> GenreRequestDto.builder()
                                    .genre(profileGenre.getGenre().getName())
                                    .build()
                    ).collect(Collectors.toList()))
                    .location(danceClass.getLocation())
                    .method(danceClass.getMethod().toString())
                    .thumbnailUrl(danceClass.getClassVideo().getThumbnailUrl())
                    .mon(danceClass.isMon())
                    .tue(danceClass.isTue())
                    .wed(danceClass.isWed())
                    .thu(danceClass.isThu())
                    .fri(danceClass.isFri())
                    .sat(danceClass.isSat())
                    .sun(danceClass.isSun())
                    .date(danceClass.getDate())
                    .state(danceClass.getState().toString()).build()
            );
        }

        return danceClassListDto;
    }

    public void deleteClass(User user, Long classId) throws BaseException {

        Optional<DanceClass> danceClass = danceClassRepository.findById(classId);

        if(!danceClass.get().getUser().getId().equals(user.getId())){
            throw new BaseException(FAIL_BAD_REQUEST);
        }

        danceClass.get().updateState(State.Delete);
        danceClassRepository.save(danceClass.get());
    }

    public void closeClass(User user, Long classId) throws BaseException{

        Optional<DanceClass> danceClass = danceClassRepository.findById(classId);

        if(!danceClass.get().getUser().getId().equals(user.getId())){
            throw new BaseException(FAIL_BAD_REQUEST);
        }

        danceClass.get().updateState(State.Closed);
        danceClassRepository.save(danceClass.get());
    }

    public GetDanceClassDto detailClass(User user, Long classId){

        Optional<DanceClass> danceClass = danceClassRepository.findById(classId);
        Optional<Profile> userprofile = profileRepository.findByUser(danceClass.get().getUser());


        GetDanceClassDto getDanceClassDto = GetDanceClassDto.builder()
                .userId(danceClass.get().getUser().getId())
                .userNickname(userprofile.get().getNickname())
                .userProfileImage(userprofile.get().getProfileImage().getUrl())
                .title(danceClass.get().getTitle())
                .thumbnailUrl(danceClass.get().getClassVideo().getThumbnailUrl())
                .videoUrl(danceClass.get().getClassVideo().getVideoUrl())
                .hashtag1(danceClass.get().getHashtag1())
                .hashtag2(danceClass.get().getHashtag2())
                .hashtag3(danceClass.get().getHashtag3())
                .genres(danceClass.get().getClassGenres().stream().map(
                                profileGenre -> GenreRequestDto.builder()
                                        .genre(profileGenre.getGenre().getName())
                                        .build()
                ).collect(Collectors.toList()))
                .location(danceClass.get().getLocation())
                .difficulty(danceClass.get().getDifficulty().toString())
                .tuition(danceClass.get().getTuition())
                .maxPeople(danceClass.get().getMaxPeople())
                .song(danceClass.get().getSong())
                .detail1(danceClass.get().getDetail1())
                .detail2(danceClass.get().getDetail2())
                .detail3(danceClass.get().getDetail3())
                .method(danceClass.get().getMethod().toString())
                .mon(danceClass.get().isMon())
                .tue(danceClass.get().isTue())
                .wed(danceClass.get().isWed())
                .thu(danceClass.get().isThu())
                .fri(danceClass.get().isFri())
                .sat(danceClass.get().isSat())
                .sun(danceClass.get().isSun())
                .startTime(danceClass.get().getStartTime())
                .endTime(danceClass.get().getEndTime())
                .date(danceClass.get().getDate())
                .reserveLink(danceClass.get().getReserveLink())
                .state(danceClass.get().getState().toString())
                .build();

        return getDanceClassDto;
    }

}