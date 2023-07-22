package com.dansup.server.api.profile.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFileUrlDto {

    @ApiModelProperty(value = "file url", example = "url")
    private String url;

}
