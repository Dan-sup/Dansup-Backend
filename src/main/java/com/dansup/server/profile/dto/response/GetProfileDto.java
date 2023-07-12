package com.dansup.server.profile.dto.response;

import com.dansup.server.auth.dto.request.GenreRequestDto;
import com.dansup.server.auth.dto.request.HashtagRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel
public class GetProfileDto {

    @ApiModelProperty(value = "댄서명", example = "유나")
    private String nickname;

    @ApiModelProperty(value = "한줄 소개", example = "나는 춤이 정말 좋아")
    private String intro;

    @ApiModelProperty(value = "댄스 장르 리스트")
    private List<GenreRequestDto> genres;

    @ApiModelProperty(value = "해시태그 리스트")
    private List<HashtagRequestDto> hashtags;

}
