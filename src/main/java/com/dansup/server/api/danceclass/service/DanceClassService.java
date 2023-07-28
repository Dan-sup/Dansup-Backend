package com.dansup.server.api.danceclass.service;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.danceclass.domain.ClassVideo;
import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.domain.State;
import com.dansup.server.api.danceclass.dto.request.CreateDanceClassDto;
import com.dansup.server.api.danceclass.dto.request.DanceClassFilterDto;
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

        String videoUrl = s3UploaderService.videoUpload(videofile);
        String thumbnailUrl = s3UploaderService.imageUpload(thumbnail);

        ClassVideo classVideo = ClassVideo.builder()
                .videoUrl(videoUrl)
                .thumbnailUrl(thumbnailUrl)
                .build();

        classVideoRepository.save(classVideo);

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

    public List<GetDanceClassListDto> getAllClassList(){

        List<DanceClass> danceClasses = danceClassRepository.findByState(State.Active);

        return createDanceClassListDtos(danceClasses);
    }

    public void deleteClass(User user, Long classId) throws BaseException {

        DanceClass danceClass = loadDanceClass(classId);

        compareUser(danceClass, user);

        danceClass.updateState(State.Delete);
        danceClassRepository.save(danceClass);
    }

    public void closeClass(User user, Long classId) throws BaseException{

        DanceClass danceClass = loadDanceClass(classId);

        compareUser(danceClass, user);

        danceClass.updateState(State.Closed);
        danceClassRepository.save(danceClass);
    }

    public GetDanceClassDto detailClass(Long classId) throws BaseException {

        DanceClass danceClass = loadDanceClass(classId);

        Profile userprofile = loadProfile(danceClass.getUser());


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
                .difficulty(danceClass.getDifficulty().getDifficulty())
                .tuition(danceClass.getTuition())
                .maxPeople(danceClass.getMaxPeople())
                .song(danceClass.getSong())
                .detail1(danceClass.getDetail1())
                .detail2(danceClass.getDetail2())
                .detail3(danceClass.getDetail3())
                .method(danceClass.getMethod().getMethod())
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


    public List<GetDanceClassListDto> getDanceClassListByFilter(String title, DanceClassFilterDto danceClassFilterDto) {

        List<DanceClass> danceClasses = new ArrayList<>();

        if(title == null) {
            danceClasses = danceClassRepository.findDanceClass(danceClassFilterDto);
        } else {
            danceClasses = danceClassRepository.findDanceClass(title, danceClassFilterDto);
        }
        return createDanceClassListDtos(danceClasses);
    }

    private List<GetDanceClassListDto> createDanceClassListDtos(List<DanceClass> danceClasses) throws BaseException {
        List<GetDanceClassListDto> danceClassListDtos = new ArrayList<>();
        Profile profile;

        for(DanceClass danceClass : danceClasses) {

            profile = loadProfile(danceClass.getUser());

            danceClassListDtos.add(GetDanceClassListDto.builder()
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
                    .method(danceClass.getMethod().getMethod())
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

        return danceClassListDtos;

    }

    private DanceClass loadDanceClass(Long classId) {

        return danceClassRepository.findById(classId).orElseThrow(
                () -> new BaseException(CLASS_NOT_FOUND)
        );
    }

    private void compareUser(DanceClass danceClass, User user) {

        if(!danceClass.getUser().getId().equals(user.getId())){
            throw new BaseException(FAIL_BAD_REQUEST);
        }
    }

    private Profile loadProfile(User user){
        return profileRepository.findByUser(user).orElseThrow(
                () -> new BaseException(ResponseCode.PROFILE_NOT_FOUND)
        );
    }
}