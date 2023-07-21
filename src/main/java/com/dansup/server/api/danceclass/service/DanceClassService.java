package com.dansup.server.api.danceclass.service;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.danceclass.domain.ClassVideo;
import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.domain.State;
import com.dansup.server.api.danceclass.dto.request.CreateDanceClassDto;
import com.dansup.server.api.danceclass.dto.response.DayResponseDto;
import com.dansup.server.api.danceclass.dto.response.GetDanceClassListDto;
import com.dansup.server.api.danceclass.repository.ClassVideoRepository;
import com.dansup.server.api.danceclass.repository.DanceClassRepository;
import com.dansup.server.api.genre.domain.ClassGenre;
import com.dansup.server.api.genre.respository.ClassGenreRepository;
import com.dansup.server.api.genre.respository.GenreRepository;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.profile.repository.ProfileRepository;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.config.s3.S3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DanceClassService {

    private final DanceClassRepository danceClassRepository;
    private final ClassVideoRepository classVideoRepository;

    private final ProfileRepository profileRepository;
    private final GenreRepository genreRepository;
    private final ClassGenreRepository classGenreRepository;
    private final S3UploaderService s3UploaderService;
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
                .hashtag1(createDanceClassDto.getHashtags().get(0).getHashtag())
                .hashtag2(createDanceClassDto.getHashtags().get(1).getHashtag())
                .hashtag3(createDanceClassDto.getHashtags().get(2).getHashtag())
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
                .build();

        danceClassRepository.save(danceClass);

        log.info("[DanceClass 생성 완료]: DanceClass_title = {}", danceClass.getTitle());

        List<GenreRequestDto> genres = createDanceClassDto.getGenres();

        for(GenreRequestDto genreRequestDto : genres) {
            if(genreRequestDto.getGenre() != null) {
                ClassGenre classGenre = ClassGenre.builder()
                        .danceClass(danceClass)
                        .genre(genreRepository.findByName(genreRequestDto.getGenre())).build();
                classGenreRepository.save(classGenre);
            }
            else break;
        }

        log.info("[DanceClass 생성 완료]: DanceClass_title = {}", danceClass.getTitle());
    }

    public List<GetDanceClassListDto> getAllClassList(User user){

        Optional<Profile> userprofile = profileRepository.findByUser(user);

        List<DanceClass> classList = new ArrayList<>();
        List<GetDanceClassListDto> danceClassListDto = new ArrayList<>();

        classList = danceClassRepository.findByState(State.Active);

        for(DanceClass danceClass : classList){

            List<GenreRequestDto> genreRequestDtos = new ArrayList<>();
            List<ClassGenre> genres = classGenreRepository.findAllByDanceClass(danceClass);
            genres.forEach(classGenre -> {
                genreRequestDtos.add(GenreRequestDto.builder().genre(classGenre.getGenre().getName()).build());
            });

            List<DayResponseDto> days = new ArrayList<>();
            if(danceClass.isMon())
                days.add(DayResponseDto.builder().day("월").build());
            if(danceClass.isTue())
                days.add(DayResponseDto.builder().day("화").build());
            if(danceClass.isWed())
                days.add(DayResponseDto.builder().day("수").build());
            if(danceClass.isThu())
                days.add(DayResponseDto.builder().day("목").build());
            if(danceClass.isFri())
                days.add(DayResponseDto.builder().day("금").build());
            if(danceClass.isSat())
                days.add(DayResponseDto.builder().day("토").build());
            if(danceClass.isSun())
                days.add(DayResponseDto.builder().day("일").build());

            danceClassListDto.add(GetDanceClassListDto.builder()
                            .userId(user.getId())
                            .userNickname(userprofile.get().getNickname())
                            .userProfileImage(userprofile.get().getProfileImage().getUrl())
                            .danceClassId(danceClass.getId())
                            .title(danceClass.getTitle())
                            .genres(genreRequestDtos)
                            .location(danceClass.getLocation())
                            .method(danceClass.getMethod().toString()) //method에 value 추가하기 또는 프론트와 상의하기
                            .thumbnailUrl(danceClass.getClassVideo().getThumbnailUrl())
                            .days(days)
                            .date(danceClass.getDate()).build());
        }
        return danceClassListDto;
    }

    public Optional<DanceClass> getClass(Long danceClassId){
        return danceClassRepository.findById(danceClassId);
    }

}
