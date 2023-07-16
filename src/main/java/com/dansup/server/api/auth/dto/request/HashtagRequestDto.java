package com.dansup.server.api.auth.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel
public class HashtagRequestDto {

    @ApiModelProperty(value = "해시태그", example = "#선위주의")
    private String hashtag;

    public void updateHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
