package com.dansup.server.danceclass.dto.response;

import com.dansup.server.auth.dto.request.GenreRequestDto;
import com.dansup.server.auth.dto.request.HashtagRequestDto;
import com.dansup.server.danceclass.domain.DanceClass;
import com.dansup.server.danceclass.domain.Difficulty;
import com.dansup.server.danceclass.domain.Method;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class GetDanceClassDto {

    //수업에서 표출되는 댄서 정보
    private final Long userId;
    private final String userNickname;
    private final String userProfileImage;

    //수업 정보
    private final String title;

    private List<HashtagRequestDto> hashtags;

    private List<GenreRequestDto> genres;
    private final String location;
    private final String difficulty;
    private final int tuition;
    private final int maxPeople;
    private final String song;
    private final String detail1;
    private final String detail2;
    private final String detail3;
    private final String method;
    private final boolean mon;
    private final boolean tue;
    private final boolean wed;
    private final boolean thu;
    private final boolean fri;
    private final boolean sat;
    private final boolean sun;
    private final int startTime;
    private final int endTime;
    private final String date;
    private final String reserveLink;

    public GetDanceClassDto(DanceClass danceClass) {
        this.userId = danceClass.getUser().getId();
        this.userNickname = danceClass.getUser().getProfile().getNickname();
        this.userProfileImage = danceClass.getUser().getProfile().getProfileImage().getUrl();
        this.title = danceClass.getTitle();
        this.hashtags.get(0).updateHashtag(danceClass.getHashtag1());
        this.hashtags.get(1).updateHashtag(danceClass.getHashtag2());
        this.hashtags.get(2).updateHashtag(danceClass.getHashtag3());
        this.location = danceClass.getLocation();
        this.difficulty = danceClass.getDifficulty().toString();
        this.tuition = danceClass.getTuition();
        this.maxPeople = danceClass.getMaxPeople();
        this.song = danceClass.getSong();
        this.detail1 = danceClass.getDetail1();
        this.detail2 = danceClass.getDetail2();
        this.detail3 = danceClass.getDetail3();
        this.method = danceClass.getMethod().toString();
        this.mon = danceClass.isMon();
        this.tue = danceClass.isTue();
        this.wed = danceClass.isWed();
        this.thu = danceClass.isThu();
        this.fri = danceClass.isFri();
        this.sat = danceClass.isSat();
        this.sun = danceClass.isSun();
        this.startTime = danceClass.getStartTime();
        this.endTime = danceClass.getEndTime();
        this.date = danceClass.getDate();
        this.reserveLink = danceClass.getReserveLink();
    }
}
