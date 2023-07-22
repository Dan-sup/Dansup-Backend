package com.dansup.server.api.profile.dto.response;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@ApiModel
public class GetProfileDetailDto {

    @ApiModelProperty(value = "유저 계정", example = "@younaring__")
    private String username;

    @ApiModelProperty(value = "댄서명", example = "유나")
    private String nickname;

    @ApiModelProperty(value = "프로필 이미지 url", example = "url.jpg")
    private String profileImageUrl;

    @ApiModelProperty(value = "프로필 동영상 url", example = "url.mp4")
    private String profileVideoUrl;

    @ApiModelProperty(value = "한줄 소개", example = "나는 춤이 정말 좋아")
    private String intro;

    @ApiModelProperty(value = "댄스 장르 리스트")
    private List<GenreRequestDto> genres;

    @ApiModelProperty(value = "해시태그1", example = "#하이하이")
    private String hashtag1;

    @ApiModelProperty(value = "해시태그2", example = "#하이하이")
    private String hashtag2;

    @ApiModelProperty(value = "해시태그3", example = "#하이하이")
    private String hashtag3;

    @Builder
    public GetProfileDetailDto(String username, String nickname, String intro,
                               List<GenreRequestDto> genres, String hashtag1, String hashtag2, String hashtag3,
                               String profileImageUrl, String profileVideoUrl) {
        this.username = username;
        this.nickname = nickname;
        this.intro = intro;
        this.genres = genres;
        this.hashtag1 = hashtag1;
        this.hashtag2 = hashtag2;
        this.hashtag3 = hashtag3;
        this.profileImageUrl = profileImageUrl;
        this.profileVideoUrl = profileVideoUrl;
    }

}