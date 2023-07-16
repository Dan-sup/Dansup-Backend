package com.dansup.server.api.auth.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel
public class GenreRequestDto {

    @ApiModelProperty(value = "댄스 장트")
    private String genre;

}
