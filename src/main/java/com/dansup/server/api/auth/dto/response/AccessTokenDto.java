package com.dansup.server.api.auth.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@ApiModel
@Builder
public class AccessTokenDto {

    @ApiModelProperty(value = "액세스 토큰")
    private String accessToken;

}
