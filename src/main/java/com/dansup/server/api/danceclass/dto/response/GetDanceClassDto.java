package com.dansup.server.api.danceclass.dto.response;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.auth.dto.request.HashtagRequestDto;
import com.dansup.server.api.danceclass.domain.DanceClass;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDanceClassDto {

    //수업에서 표출되는 댄서 정보
    @ApiModelProperty(value = "강사(댄서) 프로필 ID" , example = "4")
    private Long profileId;
    @ApiModelProperty(value = "강사(댄서) 닉네임" , example = "아이키")
    private String userNickname;
    @ApiModelProperty(value = "강사(댄서) 프로필 이미지" , example = "image")
    private String userProfileImage;

    //수업 정보
    @ApiModelProperty(value = "수업 제목" , example = "임선생의 몸치 탈출 프로젝트")
    private String title;
    @ApiModelProperty(value = "수업 영상" , example = "videoUrl")
    private String videoUrl;
    @ApiModelProperty(value = "수업 해시태그" , example = "빠른템포의")
    private String hashtag1;
    @ApiModelProperty(value = "수업 해시태그" , example = "빠른템포의")
    private String hashtag2;
    @ApiModelProperty(value = "수업 해시태그" , example = "빠른템포의")
    private String hashtag3;
    @ApiModelProperty(value = "수업 장르" , example = "락킹")
    private List<GenreRequestDto> genres;
    @ApiModelProperty(value = "수업 장소" , example = "서울특별시 강남구 00로 000")
    private String location;
    @ApiModelProperty(value = "수업 난이도" , example = "BA")
    @NotBlank
    private String difficulty;

    @ApiModelProperty(value = "수업 수강료" , example = "30000")
    @NotBlank
    private int tuition;

    @ApiModelProperty(value = "수업 총원" , example = "10")
    private int maxPeople;
    @ApiModelProperty(value = "수업 노래 제목" , example = "뉴진스 - OMG")
    private String song;
    @ApiModelProperty(value = "수업 추가 설명1" , example = "이런 것들을 배울 거예요")
    private String detail1;
    @ApiModelProperty(value = "수업 추가 설명2" , example = "이런 분들을 위한 레슨이에요")
    private String detail2;
    @ApiModelProperty(value = "수업 추가 설명3" , example = "드리는 인사말")
    private String detail3;
    @ApiModelProperty(value = "수업 방식" , example = "OD")
    private String method;
    @ApiModelProperty(value = "월요일" , example = "true")
    private boolean mon;

    @ApiModelProperty(value = "화요일" , example = "true")
    private boolean tue;

    @ApiModelProperty(value = "수요일" , example = "false")
    private boolean wed;

    @ApiModelProperty(value = "목요일" , example = "true")
    private boolean thu;

    @ApiModelProperty(value = "금요일" , example = "false")
    private boolean fri;

    @ApiModelProperty(value = "토요일" , example = "true")
    private boolean sat;

    @ApiModelProperty(value = "일요일" , example = "true")
    private boolean sun;

    @ApiModelProperty(value = "수업 시작 시간" , example = "20:00")
    private String startTime;
    @ApiModelProperty(value = "수업 종료 시간" , example = "22:00")
    private String endTime;
    @ApiModelProperty(value = "원데이 클래스에서의 날짜" , example = "2023/5/29")
    private String date;
    @ApiModelProperty(value = "현장 결제 등록" , example = "1")
    private boolean onSite;
    @ApiModelProperty(value = "수업 예약 링크" , example = "com.googleform.")
    private String reserveLink;

    @ApiModelProperty(value = "수업 상태(Active, Closed, Deleted)" , example = "Active")
    private String state;

//    public GetDanceClassDto(DanceClass danceClass) {
//        this.userId = danceClass.getUser().getId();
//        this.userNickname = danceClass.getUser().getProfile().getNickname();
//        this.userProfileImage = danceClass.getUser().getProfile().getProfileImage().getUrl();
//        this.title = danceClass.getTitle();
//        this.hashtags.get(0).updateHashtag(danceClass.getHashtag1());
//        this.hashtags.get(1).updateHashtag(danceClass.getHashtag2());
//        this.hashtags.get(2).updateHashtag(danceClass.getHashtag3());
//        this.location = danceClass.getLocation();
//        this.difficulty = danceClass.getDifficulty().toString();
//        this.tuition = danceClass.getTuition();
//        this.maxPeople = danceClass.getMaxPeople();
//        this.song = danceClass.getSong();
//        this.detail1 = danceClass.getDetail1();
//        this.detail2 = danceClass.getDetail2();
//        this.detail3 = danceClass.getDetail3();
//        this.method = danceClass.getMethod().toString();
//        this.mon = danceClass.isMon();
//        this.tue = danceClass.isTue();
//        this.wed = danceClass.isWed();
//        this.thu = danceClass.isThu();
//        this.fri = danceClass.isFri();
//        this.sat = danceClass.isSat();
//        this.sun = danceClass.isSun();
//        this.startTime = danceClass.getStartTime();
//        this.endTime = danceClass.getEndTime();
//        this.date = danceClass.getDate();
//        this.reserveLink = danceClass.getReserveLink();
//        this.state = danceClass.getState().toString();
//    }
}
