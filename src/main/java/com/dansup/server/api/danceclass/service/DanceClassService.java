package com.dansup.server.api.danceclass.service;

import com.dansup.server.api.danceclass.domain.ClassVideo;
import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.dto.request.CreateDanceClassDto;
import com.dansup.server.api.danceclass.repository.ClassVideoRepository;
import com.dansup.server.api.danceclass.repository.DanceClassRepository;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.config.s3.S3UploaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DanceClassService {

    private final DanceClassRepository danceClassRepository;
    private final ClassVideoRepository classVideoRepository;
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
    }
    public Optional<DanceClass> getClass(Long danceClassId){
        return danceClassRepository.findById(danceClassId);
    }

}
