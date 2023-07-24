package com.dansup.server.api.profile.dto.response;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProfileListDto {

    @ApiModelProperty(value = "프로필 ID", example = "23")
    private Long profileId;

    @ApiModelProperty(value = "댄서명", example = "유나")
    private String nickname;

    @ApiModelProperty(value = "한줄 소개", example = "나는 춤이 정말 좋아")
    private String intro;

    @ApiModelProperty(value = "댄서 프로필 이미지", example = "image.jpg")
    private String profileImage;

    @ApiModelProperty(value = "댄스 장르 리스트")
    private List<GenreRequestDto> genres;

    @ApiModelProperty(value = "해시태그1", example = "#하이하이")
    private String hashtag1;

    @ApiModelProperty(value = "해시태그2", example = "#하이하이")
    private String hashtag2;

    @ApiModelProperty(value = "해시태그3", example = "#하이하이")
    private String hashtag3;

}
