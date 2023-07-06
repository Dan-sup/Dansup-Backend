package com.dansup.server.danceclass.dto.request;

import com.dansup.server.auth.dto.request.GenreRequestDto;
import com.dansup.server.auth.dto.request.HashtagRequestDto;
import com.dansup.server.danceclass.domain.DanceClass;
import com.dansup.server.danceclass.domain.Difficulty;
import com.dansup.server.danceclass.domain.Method;
import com.dansup.server.user.domain.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class CreateDanceClassDto {

    @NotBlank
    private String title;

    private List<HashtagRequestDto> hashtags;

    private List<GenreRequestDto> genres;

    @NotBlank
    private String location;

    @NotBlank
    private Difficulty difficulty;

    @NotBlank
    private int tuition;

    private int maxPeople;

    @Size(min = 1, max = 10, message = "노래 제목은 최대 10자입니다.")
    private String song;

    @Size(min = 1, max = 255, message = "내용은 최대 255자입니다.")
    private String detail1;

    @Size(min = 1, max = 255, message = "내용은 최대 255자입니다.")
    private String detail2;

    @Size(min = 1, max = 255, message = "내용은 최대 255자입니다.")
    private String detail3;

    @NotBlank
    private Method method;

    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;

    @Column(nullable = false)
    @NotBlank(message = "수업 시작시간은 필수 값입니다.")
    private int startTime;

    @Column(nullable = false)
    @NotBlank(message = "수업 종료시간은 필수 값입니다.")
    private int endTime;

    private String date;

    private String reserveLink;

    private MultipartFile classVideo; // 수업 영상

    public DanceClass toDanceClassEntity(User user) {
        return DanceClass.builder()
                .user(user)
                .title(title)
                .hashtag1(hashtags.get(0).getHashtag())
                .hashtag2(hashtags.get(1).getHashtag())
                .hashtag3(hashtags.get(2).getHashtag())
                .location(location)
                .difficulty(difficulty)
                .tuition(tuition)
                .maxPeople(maxPeople)
                .song(song)
                .detail1(detail1)
                .detail2(detail2)
                .detail3(detail3)
                .method(method)
                .mon(mon)
                .tue(tue)
                .wed(wed)
                .thu(thu)
                .fri(fri)
                .sat(sat)
                .sun(sun)
                .startTime(startTime)
                .endTime(endTime)
                .date(date)
                .reserveLink(reserveLink)
                .build();
    }

}
