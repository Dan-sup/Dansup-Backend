package com.dansup.server.api.danceclass.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel
public class DayRequestDto {

    @ApiModelProperty(value = "월요일 선택" , example = "1")
    private boolean mon = false;
    @ApiModelProperty(value = "화요일 선택" , example = "1")
    private boolean tue = false;
    @ApiModelProperty(value = "수요일 선택" , example = "1")
    private boolean wed = false;
    @ApiModelProperty(value = "목요일 선택" , example = "1")
    private boolean thu = false;
    @ApiModelProperty(value = "금요일 선택" , example = "1")
    private boolean fri = false;
    @ApiModelProperty(value = "토요일 선택" , example = "1")
    private boolean sat = false;
    @ApiModelProperty(value = "일요일 선택" , example = "1")
    private boolean sun = false;
}
