package com.dansup.server.api.danceclass.dto.request;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.auth.dto.request.HashtagRequestDto;
import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.domain.Difficulty;
import com.dansup.server.api.danceclass.domain.Method;
import com.dansup.server.api.danceclass.dto.response.DayResponseDto;
import com.dansup.server.api.user.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@ApiModel
public class CreateDanceClassDto {

    @ApiModelProperty(value = "수업 제목" , example = "임선생의 몸치 탈출 프로젝트")
    @NotBlank
    private String title;

    @ApiModelProperty(value = "수업 해시태그" , example = "빠른템포의")
    private String hashtag1;

    @ApiModelProperty(value = "수업 해시태그" , example = "빠른템포의")
    private String hashtag2;

    @ApiModelProperty(value = "수업 해시태그" , example = "빠른템포의")
    private String hashtag3;

    @ApiModelProperty(value = "수업 장르" , example = "락킹", notes = "장르 5개, 없으면 null로 주세요!")
    private List<GenreRequestDto> genres;

    @ApiModelProperty(value = "수업 장소" , example = "서울특별시 강남구 00로 000")
    @NotBlank
    private String location;

    @ApiModelProperty(value = "수업 난이도" , example = "BA")
    @NotBlank
    private Difficulty difficulty;

    @ApiModelProperty(value = "수업 수강료" , example = "30000")
    @NotBlank
    private int tuition;

    @ApiModelProperty(value = "수업 총원" , example = "10")
    private int maxPeople;

    @ApiModelProperty(value = "수업 노래 제목" , example = "뉴진스 - OMG")
    @Size(min = 1, max = 20, message = "노래 제목은 최대 20자입니다.")
    private String song;

    @ApiModelProperty(value = "수업 추가 설명1" , example = "이런 것들을 배울 거예요")
    @Size(min = 1, max = 255, message = "내용은 최대 255자입니다.")
    private String detail1;

    @ApiModelProperty(value = "수업 추가 설명2" , example = "이런 분들을 위한 레슨이에요")
    @Size(min = 1, max = 255, message = "내용은 최대 255자입니다.")
    private String detail2;

    @ApiModelProperty(value = "수업 추가 설명3" , example = "드리는 인사말")
    @Size(min = 1, max = 255, message = "내용은 최대 255자입니다.")
    private String detail3;

    @ApiModelProperty(value = "수업 방식" , example = "OD")
    @NotBlank
    private Method method;

    @ApiModelProperty(value = "수업 요일 선택", notes = "선택된 날짜를 true 로 변경해서 보내주세요!")
    private DayRequestDto days;

    @ApiModelProperty(value = "수업 시작 Hour(int)" , example = "20")
    private int startHour;

    @ApiModelProperty(value = "수업 시작 Time(string)" , example = "20:00")
    private String startTime;

    @ApiModelProperty(value = "수업 종료 Hour(int)" , example = "22")
    private int endHour;

    @ApiModelProperty(value = "수업 종료 Time(string)" , example = "22:00")
    private String endTime;

    @ApiModelProperty(value = "원데이 클래스에서의 날짜" , example = "2023/5/29")
    private String date;

    @ApiModelProperty(value = "수업 예약 링크" , example = "com.googleform.")
    private String reserveLink;


//    public DanceClass toDanceClassEntity(User user) {
//        return DanceClass.builder()
//                .user(user)
//                .title(title)
//                .hashtag1(hashtags.get(0).getHashtag())
//                .hashtag2(hashtags.get(1).getHashtag())
//                .hashtag3(hashtags.get(2).getHashtag())
//                .location(location)
//                .difficulty(difficulty)
//                .tuition(tuition)
//                .maxPeople(maxPeople)
//                .song(song)
//                .detail1(detail1)
//                .detail2(detail2)
//                .detail3(detail3)
//                .method(method)
//                .mon(mon)
//                .tue(tue)
//                .wed(wed)
//                .thu(thu)
//                .fri(fri)
//                .sat(sat)
//                .sun(sun)
//                .startTime(startTime)
//                .endTime(endTime)
//                .date(date)
//                .reserveLink(reserveLink)
//                .build();
//    }

}
