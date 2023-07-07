package com.dansup.server.danceclass.dto.response;

import com.dansup.server.auth.dto.request.GenreRequestDto;
import com.dansup.server.danceclass.domain.DanceClass;
import lombok.Getter;

import java.util.List;

@Getter
public class GetDanceClassListDto {

    //수업에서 표출되는 댄서 정보
    private final Long userId;
    private final String userNickname;
    private final String userProfileImage;
    private final String title;
    private List<GenreRequestDto> genres;
    private final String location;
    private final String method;
    private List<DayResponseDto> days;
    private String date;

    private String state;

    public GetDanceClassListDto(DanceClass danceClass) {
        this.userId = danceClass.getUser().getId();
        this.userNickname = danceClass.getUser().getProfile().getNickname();
        this.userProfileImage = danceClass.getUser().getProfile().getProfileImage().getUrl();
        this.title = danceClass.getTitle();
        this.location = danceClass.getLocation();
        this.method = danceClass.getMethod().toString();
        this.date = danceClass.getDate();
        this.state = danceClass.getState().toString();
    }
//        if(danceClass.isMon() == true){
//            this.days.
//        }
}
