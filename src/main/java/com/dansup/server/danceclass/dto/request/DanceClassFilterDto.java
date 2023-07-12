package com.dansup.server.danceclass.dto.request;

import com.dansup.server.auth.dto.request.GenreRequestDto;
import com.dansup.server.danceclass.domain.Difficulty;
import com.dansup.server.danceclass.domain.Method;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@ApiModel
public class DanceClassFilterDto {

    @ApiModelProperty(value = "수업 장소" , example = "강남구")
    private String local;

    @ApiModelProperty(value = "수업 장르" , example = "락킹")
    private List<GenreRequestDto> genres;

    @ApiModelProperty(value = "월요일 선택" , example = "1")
    private boolean mon;
    @ApiModelProperty(value = "화요일 선택" , example = "1")
    private boolean tue;
    @ApiModelProperty(value = "수요일 선택" , example = "1")
    private boolean wed;
    @ApiModelProperty(value = "목요일 선택" , example = "1")
    private boolean thu;
    @ApiModelProperty(value = "금요일 선택" , example = "1")
    private boolean fri;
    @ApiModelProperty(value = "토요일 선택" , example = "1")
    private boolean sat;
    @ApiModelProperty(value = "일요일 선택" , example = "1")
    private boolean sun;

    @ApiModelProperty(value = "수업 시작 시간" , example = "20")
    private int startTime;

    @ApiModelProperty(value = "수업 종료 시간" , example = "22")
    private int endTime;

    @ApiModelProperty(value = "수업 방식" , example = "O")
    private Method method;

    @ApiModelProperty(value = "수업 난이도" , example = "BA")
    private Difficulty difficulty;

    @ApiModelProperty(value = "수업 수강료 - ~원 미만" , example = "30000")
    private int tuition;
}
