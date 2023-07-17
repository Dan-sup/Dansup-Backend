package com.dansup.server.api.auth.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel
@Getter
public class RefreshTokenDto {

    @ApiModelProperty(value = "리프레쉬 토큰")
    private String refreshToken;

}
