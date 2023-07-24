package com.dansup.server.api.danceclass.service;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.danceclass.domain.ClassVideo;
import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.domain.State;
import com.dansup.server.api.danceclass.dto.request.CreateDanceClassDto;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassDto;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.api.danceclass.repository.ClassVideoRepository;
import com.dansup.server.api.danceclass.repository.DanceClassRepository;
import com.dansup.server.api.genre.GenreService;
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
import java.util.stream.Collectors;

import static com.dansup.server.common.response.ResponseCode.CLASS_NOT_FOUND;
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
                                    .genre(
                                            (profileGenre.getGenre() != null) ?
                                            profileGenre.getGenre().getName() : null
                                    )
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

        DanceClass danceClass = danceClassRepository.findById(classId).orElseThrow(
                () -> new BaseException(CLASS_NOT_FOUND)
        );

        if(!danceClass.getUser().getId().equals(user.getId())){
            throw new BaseException(FAIL_BAD_REQUEST);
        }

        danceClass.updateState(State.Delete);
        danceClassRepository.save(danceClass);
    }

    public void closeClass(User user, Long classId) throws BaseException{

        DanceClass danceClass = danceClassRepository.findById(classId).orElseThrow(
                () -> new BaseException(CLASS_NOT_FOUND)
        );

        if(!danceClass.getUser().getId().equals(user.getId())){
            throw new BaseException(FAIL_BAD_REQUEST);
        }

        danceClass.updateState(State.Closed);
        danceClassRepository.save(danceClass);
    }

    public GetDanceClassDto detailClass(User user, Long classId){

        DanceClass danceClass = danceClassRepository.findById(classId).orElseThrow(
                () -> new BaseException(CLASS_NOT_FOUND)
        );
        Profile userprofile = profileRepository.findByUser(danceClass.getUser()).orElseThrow(
                () -> new BaseException(ResponseCode.PROFILE_NOT_FOUND)
        );

        GetDanceClassDto getDanceClassDto = GetDanceClassDto.builder()
                .userId(danceClass.getUser().getId())
                .userNickname(userprofile.getNickname())
                .userProfileImage(userprofile.getProfileImage().getUrl())
                .title(danceClass.getTitle())
                .thumbnailUrl(danceClass.getClassVideo().getThumbnailUrl())
                .videoUrl(danceClass.getClassVideo().getVideoUrl())
                .hashtag1(danceClass.getHashtag1())
                .hashtag2(danceClass.getHashtag2())
                .hashtag3(danceClass.getHashtag3())
                .genres(danceClass.getClassGenres().stream().map(
                                profileGenre -> GenreRequestDto.builder()
                                        .genre(
                                                (profileGenre.getGenre() != null) ?
                                                profileGenre.getGenre().getName() : null
                                        )
                                        .build()
                ).collect(Collectors.toList()))
                .location(danceClass.getLocation())
                .difficulty(danceClass.getDifficulty().toString())
                .tuition(danceClass.getTuition())
                .maxPeople(danceClass.getMaxPeople())
                .song(danceClass.getSong())
                .detail1(danceClass.getDetail1())
                .detail2(danceClass.getDetail2())
                .detail3(danceClass.getDetail3())
                .method(danceClass.getMethod().toString())
                .mon(danceClass.isMon())
                .tue(danceClass.isTue())
                .wed(danceClass.isWed())
                .thu(danceClass.isThu())
                .fri(danceClass.isFri())
                .sat(danceClass.isSat())
                .sun(danceClass.isSun())
                .startTime(danceClass.getStartTime())
                .endTime(danceClass.getEndTime())
                .date(danceClass.getDate())
                .reserveLink(danceClass.getReserveLink())
                .state(danceClass.getState().toString())
                .build();

        return getDanceClassDto;
    }

}