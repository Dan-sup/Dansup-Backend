package com.dansup.server.api.danceclass.dto.request;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.danceclass.domain.Difficulty;
import com.dansup.server.api.danceclass.domain.Method;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel
public class DanceClassFilterDto {

    @ApiModelProperty(value = "수업 장소" , example = "강남구")
    private String location;

    @ApiModelProperty(value = "수업 장르" , example = "락킹")
    private List<GenreRequestDto> genres;

    @ApiModelProperty(value = "수업 요일 선택" , notes = "선택된 날짜를 true 로 변경해서 보내주세요!")
    private DayRequestDto days;

    @ApiModelProperty(value = "수업 시간대" , example = "오전-오후")
    private String time;

    @ApiModelProperty(value = "수업 시작 시간" , example = "20")
    private Integer startHour;

    @ApiModelProperty(value = "수업 종료 시간" , example = "22")
    private Integer endHour;

    @ApiModelProperty(value = "수업 방식" , example = "OD")
    private Method method;

    @ApiModelProperty(value = "수업 난이도" , example = "BA")
    private Difficulty difficulty;

    @ApiModelProperty(value = "수업 수강료(~이상)" , example = "30000")
    private Integer minTuition;

    @ApiModelProperty(value = "수업 수강료(~이하)" , example = "60000")
    private Integer maxTuition;

}
