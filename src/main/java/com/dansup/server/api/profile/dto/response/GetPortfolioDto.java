package com.dansup.server.api.profile.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;

@Getter
@ApiModel
public class GetPortfolioDto {

    @ApiParam(value = "날짜", example = "2023.05.10.")
    private String date;

    @ApiModelProperty(value = "경력 세부 정보", example = "00 대회 우승")
    private String detail;

}
