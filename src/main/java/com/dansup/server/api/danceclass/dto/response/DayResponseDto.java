package com.dansup.server.api.danceclass.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@ApiModel
public class DayResponseDto {
    @ApiModelProperty(value = "수업 요일" , example = "월")
    private String day;

    public void setDay(String day) {
        this.day = day;
    }
}
