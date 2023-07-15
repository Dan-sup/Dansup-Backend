package com.dansup.server.api.auth.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;

@Getter
@ApiModel
public class GenreRequestDto {

    @ApiParam(value = "댄스 장트")
    private String genre;

}
