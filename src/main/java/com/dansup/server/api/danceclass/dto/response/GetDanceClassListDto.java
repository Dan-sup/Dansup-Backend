package com.dansup.server.api.danceclass.dto.response;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.danceclass.domain.DanceClass;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDanceClassListDto {

    //수업에서 표출되는 댄서 정보
    @ApiModelProperty(value = "강사(댄서) ID" , example = "4")
    private Long userId;

    @ApiModelProperty(value = "강사(댄서) 닉네임" , example = "아이키")
    private String userNickname;

    @ApiModelProperty(value = "강사(댄서) 프로필 이미지" , example = "image")
    private String userProfileImage;

    @ApiModelProperty(value = "dance_class_id" , example = "4")
    private Long danceClassId;

    @ApiModelProperty(value = "수업 제목" , example = "임선생의 몸치 탈출 프로젝트")
    private String title;

    @ApiModelProperty(value = "수업 장르" , example = "락킹")
    private List<GenreRequestDto> genres;

    @ApiModelProperty(value = "수업 장소" , example = "서울특별시 강남구 00로 000")
    private String location;

    @ApiModelProperty(value = "수업 방식" , example = "OD")
    private String method;

    @ApiModelProperty(value = "수업 영상 thumbnail" , example = "Url")
    private String thumbnailUrl;

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

    @ApiModelProperty(value = "원데이 클래스에서의 날짜" , example = "2023/5/29")
    private String date;

    @ApiModelProperty(value = "수업 상태(Active, Closed, Deleted" , example = "Active")
    private String state;

}
