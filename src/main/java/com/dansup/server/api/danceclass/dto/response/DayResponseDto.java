package com.dansup.server.api.danceclass.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayResponseDto {
    @ApiModelProperty(value = "수업 요일" , example = "월")
    private String day;

    public void setDay(String day) {
        this.day = day;
    }
}
