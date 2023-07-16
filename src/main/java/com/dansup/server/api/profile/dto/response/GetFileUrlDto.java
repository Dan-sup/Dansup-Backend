package com.dansup.server.api.profile.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel
public class GetFileUrlDto {

    @ApiModelProperty(value = "file url", example = "url")
    private String url;

}
