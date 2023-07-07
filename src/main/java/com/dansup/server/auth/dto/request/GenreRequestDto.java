package com.dansup.server.auth.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;


@ApiModel
public class GenreRequestDto {

    @ApiParam(value = "댄스 장트")
    private String genre;

}
